package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.SignUpRequest;
import com.javaproject.harang.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signUp(@RequestParam String name,
                       @RequestParam Integer age,
                       @RequestParam String userId,
                       @RequestParam String password,
                       @RequestParam Integer phoneNumber,
                       @RequestParam String intro,
                       @RequestParam MultipartFile image) {

        userService.signUp(
                SignUpRequest.builder()
                        .name(name)
                        .age(age)
                        .userId(userId)
                        .password(password)
                        .phoneNumber(phoneNumber)
                        .intro(intro)
                        .image(image)
                        .build()
        );
    }


}