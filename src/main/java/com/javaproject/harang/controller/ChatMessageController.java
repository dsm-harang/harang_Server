//package com.javaproject.harang.controller;
//
//import com.javaproject.harang.payload.response.chatResponse.ChatMessageForm;
//import com.javaproject.harang.service.chat.ChatMessageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatMessageController {
//    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final ChatMessageService chatMessageService;
//    @MessageMapping("/chat/send")
//    public void sendMsg(ChatMessageForm message) {
//        Integer chatRoomId = message.getChatRoomId();
//        chatMessageService.save(message);
//        simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId,message);
//    }
//
//}
