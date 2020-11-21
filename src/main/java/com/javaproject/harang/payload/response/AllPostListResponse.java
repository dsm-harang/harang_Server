package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllPostListResponse {
    private Integer postId;
    private String postTitle;
}
