package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.ChatMessage;
import com.javaproject.harang.entity.chat.ChatMessageForm;
import com.javaproject.harang.entity.chat.ChatMessageRepository;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
//    private final CustomerRepository usersService;
    private final CustomerRepository customerRepository;
    private final ChatRoomService chatRoomService;
//    private final NoticeService noticeService;
    @Transactional
    public void save(ChatMessageForm message) {
        ChatMessage chatMessage = new ChatMessage(message.getMessage(),LocalDateTime.now(),chatRoomService.findById(message.getChatRoomId()).get()
        ,customerRepository.findByName(message.getSender()).orElseThrow());
        chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
    }
}
