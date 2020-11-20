package com.javaproject.harang.payload.request;

import lombok.Getter;

@Getter
public class SendScoreRequest {
    private Integer score;
    private String scoreContent;
    private Integer postId;
}
