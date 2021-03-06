package com.javaproject.harang.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.javaproject.harang.entity.message.Message;
import com.javaproject.harang.entity.message.MessageRoom;
import com.javaproject.harang.entity.message.MessageRoomJoin;
import com.javaproject.harang.entity.message.enumMessage.RoomStatus;
import com.javaproject.harang.entity.message.repository.MessageRepository;
import com.javaproject.harang.entity.message.repository.MessageRoomJoinRepository;
import com.javaproject.harang.entity.message.repository.MessageRoomRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomJoinNotFound;
import com.javaproject.harang.payload.request.ErrorResponse;
import com.javaproject.harang.payload.request.MessageRequest;
import com.javaproject.harang.payload.response.MessageResponse;
import com.javaproject.harang.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService {

    private final SocketIOServer socketIOServer;

    private final JwtProvider jwtTokenProvider;

    private final MessageRepository messageRepository;
    private final CustomerRepository customerRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRoomJoinRepository messageRoomJoinRepository;

    @Override
    public void connect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if (!jwtTokenProvider.validateToken(token)) {
            clientDisconnect(client, 403, "Can not resolve token");
            return;
        }

        User user;
        try {
            user = customerRepository.findById(Integer.valueOf(jwtTokenProvider.getUserId(token)))
                    .orElseThrow(RuntimeException::new);
            client.set("user", user);
        } catch (Exception e) {
            clientDisconnect(client, 404, "Could not get user");
            return;
        }
    }

    @Override
    public void disConnect(SocketIOClient client) {
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s%n", client.getSessionId()));
    }

    @Override
    public void joinRoom(SocketIOClient client, String room) {
        User user = client.get("user");

        int roomId = Integer.parseInt(room);

        if (user == null) {
            clientDisconnect(client, 403, "Invalid Connection");
            return;
        }
        try {
            MessageRoom messageRoom = messageRoomRepository.findById(roomId).get();
            messageRoomJoinRepository.findByUserIdAndRoomId(user.getId(),roomId);
            if (messageRoom.getRoomStatus().equals(RoomStatus.CLOSE)) {
                clientDisconnect(client, 401, "Room Close");
                return;
            }
        } catch (Exception e) {
            clientDisconnect(client, 404, "Not Found Room");
        }

        client.joinRoom(room);
        printLog(
                client,
                String.format("Join Room [senderId(%d) -> receiverId(%d)] Session Id: %s%n",
                        user.getId(), roomId, client.getSessionId())
        );

    }

    @Override
    public void chat(SocketIOClient client, MessageRequest messageRequest) {
        String room = messageRequest.getRoomId();
        Integer roomId = Integer.parseInt(room);
        if (!client.getAllRooms().contains(room)) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        User user = client.get("user");
        if (user == null) {
            clientDisconnect(client, 403, "Invalid Connection");
            return;
        }
        Message message = messageRepository.save(
                Message.builder()
                        .senderId(user.getId())
                        .roomId(roomId)
                        .content(messageRequest.getMessage())
                        .localDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .build()
        );

        socketIOServer.getRoomOperations(messageRequest.getRoomId()).sendEvent("receive",
                MessageResponse.builder()
                        .userId(message.getSenderId())
                        .userName(user.getName())
                        .message(messageRequest.getMessage())
                        .isMine(user.getId().equals(message.getSenderId()))
                        .build()
        );

    }

    private void printLog(SocketIOClient client, String content) {
        String stringDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.printf(
                "%s  %s - [%s] - %s",
                stringDate,
                "SOCKET",
                client.getRemoteAddress().toString().substring(1),
                content
        );
    }

    private void clientDisconnect(SocketIOClient client, Integer status, String reason) {
        client.sendEvent("error", new ErrorResponse(status, reason));
        client.disconnect();
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s - %s%n", client.getSessionId(), reason)
        );
    }

}
