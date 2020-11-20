package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPostListResponse {
    private Integer postId;
    private String postTitle;
}
