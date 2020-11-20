package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.*;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomJoinNotFound;
import com.javaproject.harang.exception.ChatRoomNotFound;
import com.javaproject.harang.exception.NotEqualsUser;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.payload.response.chatResponse.ChatMessagesResponseForm;
import com.javaproject.harang.payload.response.chatResponse.ChatRoomForm;
import com.javaproject.harang.payload.response.chatResponse.ChatUserForm;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public Map<String, Object> chatHome() {
        Map<String, Object> map = new HashMap<>();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(customer);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, customer);
        map.put("chatRooms", chatRooms);
        if (customer == null) {
            map.put("userName", "");
            map.put("userId", 0);
        } else {
            map.put("userName", customer.getName());
            map.put("userId", customer.getId());
        }
        return map;
    }

    @Override
    public void closeChat(Integer chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId).orElseThrow();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode).get();
        System.out.println(customer.getId());
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoom).orElseThrow(ChatRoomJoinNotFound::new);
        Member member = memberRepository.findByUserIdAndPostId(customer.getId(), chatRoomId).orElseThrow(UserNotFound::new);
        if (customer.equals(chatRoomJoin.getCustomer())) {
            chatRoom.setStatus("close");
            chatRoomRepository.save(chatRoom);
        }
    }

    @Override
    public List<ChatMessagesResponseForm> goChat(Integer chatRoomId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFound::new);
        List<ChatUserForm> userForms = new ArrayList<>();
        List<ChatMessagesResponseForm> messages = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageRepository.findAllByChatRoom(chatRoom)) {
            userForms.add(
                    ChatUserForm.builder()
                            .name(chatMessage.getWriter().getName())
                            .imagePath(chatMessage.getWriter().getImagePath())
                            .userId(chatMessage.getWriter().getId())
                            .message(chatMessage.getMessage())

                            .build()
            );
        }
        messages.add(
                ChatMessagesResponseForm.builder()
                        .chatRoomId(chatRoom.getId())
                        .userName(customer.getName())
                        .userId(customer.getId())
                        .message(userForms)
                        .build());
        return messages;
    }
}