package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListScoreResponse {
    private Integer postId;
    private Integer userUuid;
    private String userName;
    private String imageName;
}
