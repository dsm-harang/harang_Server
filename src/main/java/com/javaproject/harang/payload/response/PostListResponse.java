package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostListResponse {
    private Integer postId;
    private Integer userId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime meetTime;
    private String address;
    private Integer ageLimit;
    private LocalDateTime createdAt;
    private Integer personnel;
    private String postImage;
    private String tag;
    private double score;
    private String profileImage;
}
