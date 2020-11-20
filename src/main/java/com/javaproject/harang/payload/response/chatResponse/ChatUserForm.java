package com.javaproject.harang.payload.response.chatResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ChatUserForm {
    private Integer userId;
    private String imagePath;
    private String name;
    private String message;
}
