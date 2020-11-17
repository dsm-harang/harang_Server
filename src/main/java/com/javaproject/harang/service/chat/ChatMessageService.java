package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.*;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.response.chatresponse.ChatMessageForm;
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
        Customer customer = customerRepository.findByName(message.getSender()).orElseThrow();
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoomService.findById(message.getChatRoomId()).get()).orElseThrow();
        if (customer.equals(chatRoomJoin.getCustomer())) {
            ChatMessage chatMessage = new ChatMessage(message.getMessage(), LocalDateTime.now(), chatRoomService.findById(message.getChatRoomId()).get()
                    , customerRepository.findByName(message.getSender()).orElseThrow());
            chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
        }
    }
}
