package com.javaproject.harang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScoreResponse {
    private String senderName;
    private String comment;
    private double score;
    private LocalDateTime scoreAt;
}
