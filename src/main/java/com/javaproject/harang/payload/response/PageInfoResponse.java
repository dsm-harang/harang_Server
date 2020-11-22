package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfoResponse {
    private Integer id;
    private String name;
    private String intro;
    private String imagName;
}
