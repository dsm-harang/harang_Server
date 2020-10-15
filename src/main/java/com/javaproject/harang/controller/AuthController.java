package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.AccountRequest;
import com.javaproject.harang.payload.response.TokenResponse;
import com.javaproject.harang.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user")
    public TokenResponse userSignIn(@RequestBody @Valid AccountRequest Request) {
        return authService.userSignIn(Request);
    }

    @PutMapping("/user")
    public TokenResponse userRefreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return authService.userRefreshToken(refreshToken);
    }

    @PostMapping("/admin")
    public TokenResponse adminSignIn(@RequestBody @Valid AccountRequest Request) {
        return authService.adminSignIn(Request);
    }

    @PutMapping("/admin")
    public TokenResponse adminRefreshToken(@RequestHeader("X-Refresh-Token") String refreshToken) {
        return authService.adminRefreshToken(refreshToken);
    }

}
