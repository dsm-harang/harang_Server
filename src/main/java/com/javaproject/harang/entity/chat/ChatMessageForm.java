package com.javaproject.harang.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageForm {
    private Integer ChatRoomId;
    private String receiver;
    private String sender;
    private String message;
}
