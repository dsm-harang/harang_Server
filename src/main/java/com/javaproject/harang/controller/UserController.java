package com.javaproject.harang.controller;

import com.javaproject.harang.entity.report.UserReports;
import com.javaproject.harang.payload.request.SignUpRequest;
import com.javaproject.harang.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

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
                       @RequestParam @Pattern(regexp = "(^[0-9]*$)") Integer phoneNumber,
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

    @PostMapping("/report/{targetId}")
    public void userReport(@PathVariable Integer targetId,
                           @RequestParam String content) {
        userService.userReport(targetId, content);
    }
}