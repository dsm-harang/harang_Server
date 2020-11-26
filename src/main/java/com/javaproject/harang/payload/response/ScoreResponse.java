package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScoreResponse {
    private Integer userId;
    private Integer senderId;
    private String senderName;
    private String comment;
    private Integer postId;
    private double score;
    private LocalDateTime scoreAt;
}
