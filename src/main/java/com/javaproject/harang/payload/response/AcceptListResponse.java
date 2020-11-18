package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AcceptListResponse {
    private String userName;
    private Integer score;
    private String imageName;
}
