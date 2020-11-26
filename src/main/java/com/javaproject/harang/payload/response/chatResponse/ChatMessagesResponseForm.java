package com.javaproject.harang.payload.response.chatResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatMessagesResponseForm {
    private List<ChatUserForm> message;
    private String userName;
    private int userId;
    private int chatRoomId;
    public ChatMessagesResponseForm() {}
}
