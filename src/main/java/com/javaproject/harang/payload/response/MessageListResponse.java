package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageListResponse {
    private Integer userId;
    private String userName;
    private List<MessageRoomResponse> messageRoom;
}
