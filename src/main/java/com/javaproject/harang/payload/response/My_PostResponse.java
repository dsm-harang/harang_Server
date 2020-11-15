package com.javaproject.harang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class My_PostResponse {
    private String image;
    private String title;
    private String content;
}
