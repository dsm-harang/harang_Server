package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponse {
    private String title;
    private String content;
    private String writer;
    private LocalDateTime meetTime;
    private String address;
    private Integer ageLimit;
    private LocalDateTime createdAt;
    private Integer personnel;
    private String imageName;
}
