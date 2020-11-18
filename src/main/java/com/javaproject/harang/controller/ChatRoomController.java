package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.chatResponse.ChatMessagesResponseForm;

import com.javaproject.harang.service.chat.ChatRoomJoinService;
import com.javaproject.harang.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatService chatService;

    @GetMapping("/chat")
    public Map<String, Object> chatHome() {
        return chatService.chatHome();
    }
    @PutMapping("/chat/{chatRoomId}")
    public void closeChat(@PathVariable("chatRoomId") Integer chatRoomId) {
        chatService.closeChat(chatRoomId);
    }

    @PostMapping("/newChat/{postId}")
    public void newChat(@PathVariable("postId") Integer postId) {
        chatRoomJoinService.newRoom(postId);
    }

    @PostMapping("/addChat")
    public void addChat(@RequestParam Integer userId, @RequestParam Integer roomId) {
        chatRoomJoinService.addRoom(roomId, userId);
    }

    @RequestMapping("/chat/{chatRoomId}")
    public ChatMessagesResponseForm goChat(@PathVariable("chatRoomId") Integer chatRoomId) {
        return chatService.goChat(chatRoomId);
    }
}
