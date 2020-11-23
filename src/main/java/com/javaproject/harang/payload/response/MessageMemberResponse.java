package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageMemberResponse {
    private String userName;
    private double score;
    private String imageName;
}
