package com.javaproject.harang.service.room;

import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.message.MessageRoom;
import com.javaproject.harang.entity.message.MessageRoomJoin;
import com.javaproject.harang.entity.message.enumMessage.RoomStatus;
import com.javaproject.harang.entity.message.repository.MessageRoomJoinRepository;
import com.javaproject.harang.entity.message.repository.MessageRoomRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.PostNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceImpl implements MessageRoomService {

    private final PostRepository postRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRoomJoinRepository messageRoomJoinRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;

    @Override
    public void createMessageRoom(Integer userId, Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        messageRoomRepository.save(
                MessageRoom.builder()
                        .postId(postId)
                        .roomStatus(RoomStatus.OPEN)
                        .build()

        );
        messageRoomJoinRepository.save(
                MessageRoomJoin.builder()
                        .postId(postId)
                        .roomId(messageRoomRepository.findByPostId(postId).orElseThrow().getId())
                        .userId(user.getId())
                        .build()
        );
    }

    @Override
    public void addMessageRoom(Integer userId, Integer postId) {
        MessageRoom messageRoom = messageRoomRepository.findByPostId(postId)
                .orElseThrow(PostNotFound::new);

        messageRoomJoinRepository.save(
                MessageRoomJoin.builder()
                        .postId(postId)
                        .roomId(messageRoom.getId())
                        .userId(userId)
                        .build()
        );
    }

    @Override
    public void closeRoom(Integer postId) {
        MessageRoom messageRoom = messageRoomRepository.findByPostId(postId)
                .orElseThrow(PostNotFound::new);

        messageRoomRepository.save(messageRoom.updateRoomStatus(RoomStatus.CLOSE));

    }

}
