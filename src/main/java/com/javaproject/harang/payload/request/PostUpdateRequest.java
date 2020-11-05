package com.javaproject.harang.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostUpdateRequest {
    private String title;
    private String content;
    private String tag;
    private String address;
    private Integer ageLimit;
    private Integer personnel;
    private MultipartFile image;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime meetTime;
}
