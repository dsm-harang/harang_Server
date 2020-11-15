package com.javaproject.harang.payload.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class SignUpRequest {

    private String name;

    private Integer age;

    private String userId;

    private String password;

    private Integer phoneNumber;

    private MultipartFile image;

    private String intro;

}
