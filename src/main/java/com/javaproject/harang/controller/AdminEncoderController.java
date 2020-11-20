package com.javaproject.harang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("human")
@RequiredArgsConstructor
public class AdminEncoderController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{password}")
    public String adminPassword(@PathVariable String password) {
        return passwordEncoder.encode(password);
    }

}
