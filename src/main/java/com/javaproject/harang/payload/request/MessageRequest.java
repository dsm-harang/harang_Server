package com.javaproject.harang.payload.request;

import lombok.Getter;

@Getter
public class MessageRequest {
    private String roomId;
    private String message;
}
