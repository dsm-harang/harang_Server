package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserPageResponse {
    private String userId;
    private String name;
    private double score;
    private String imageName;
    private List<String> content;
}
