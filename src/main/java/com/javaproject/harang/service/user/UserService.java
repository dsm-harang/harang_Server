package com.javaproject.harang.service.user;

import com.javaproject.harang.payload.request.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest signUpRequest);
}
