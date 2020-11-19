package com.javaproject.harang.payload.response.chatResponse;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ChatRoomForm {
    private Integer id;
    private String writer;
    private String lastMessage;
    private LocalDateTime time;

    public void makeChatRoomForm(String message, String anotherUser, LocalDateTime time) {
        this.lastMessage = message;
        this.writer = anotherUser;
        this.time = time;
    }
}
