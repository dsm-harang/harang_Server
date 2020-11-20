package com.javaproject.harang.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PostReportResponse {
    private Integer id;
    private Integer postId;
    private String title;
    private String writer;
    private double score;
    private LocalDate reportTime;
}
