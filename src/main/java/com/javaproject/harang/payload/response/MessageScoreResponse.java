package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageScoreResponse {
    private Integer userId;
    private String userName;
    private double Score;
}
