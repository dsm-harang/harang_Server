package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MessageResponse {
    private Integer userId;
    private String userName;
    private String message;
    private boolean isMine;
    private List<MessageMemberResponse> messageMember;
}
