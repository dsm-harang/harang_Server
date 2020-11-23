package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.MessageResponse;
import com.javaproject.harang.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{roomId}")
    public List<MessageResponse> getMessageList(@PathVariable String roomId,
                                                @RequestParam Integer postId) {
        return messageService.getMessageList(roomId, postId);
    }
}
