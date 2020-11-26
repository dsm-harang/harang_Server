package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.MessageListResponse;
import com.javaproject.harang.payload.response.MessageResponse;
import com.javaproject.harang.payload.response.MessageScoreResponse;
import com.javaproject.harang.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{postId}")
    public List<MessageResponse> getMessageList(@PathVariable Integer postId) {
        return messageService.getMessageList(postId);
    }
    @GetMapping()
    public List<MessageListResponse> listMyRoom() {
        return messageService.listMyRoom();
    }
    @GetMapping("/score/{postId}")
    public List<MessageScoreResponse> MessageSeeScore(@PathVariable Integer postId){
        return messageService.MessageSeeScore(postId);
    }
}
