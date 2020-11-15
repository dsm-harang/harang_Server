package com.javaproject.harang.payload.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MyPageUpdateRequest {
    private Integer Id;

    private MultipartFile imagePath;

    private String intro;
}
