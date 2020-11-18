package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.*;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomJoinNotFound;
import com.javaproject.harang.exception.NotEqualsUser;
import com.javaproject.harang.exception.RoomCloseException;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.chatResponse.ChatMessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final CustomerRepository customerRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    @Transactional
    public void save(ChatMessageForm message) {
        Customer customer = customerRepository.findByName(message.getSender()).orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomService.findById(message.getChatRoomId()).get();
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoom).orElseThrow(ChatRoomJoinNotFound::new);
        if (customer.equals(chatRoomJoin.getCustomer())) {
            if (chatRoom.getStatus().equals("open")) {
                ChatMessage chatMessage = new ChatMessage(message.getMessage(), LocalDateTime.now(), chatRoom, customer);
                chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
            }
            else{
                throw new RoomCloseException();
            }
        }
        else{
            throw new NotEqualsUser();
        }
    }
}
