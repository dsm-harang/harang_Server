package com.javaproject.harang.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetPostResponse {
    private String title;
    private String content;
    private String writer;
    private String address;
    private Integer ageLimit;
    private LocalDateTime createdAt;
    private Integer personnel;
    private String image;
    private boolean isMine;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime meetTime;

}
