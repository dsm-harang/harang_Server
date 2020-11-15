package com.javaproject.harang.service.auth;

import com.javaproject.harang.payload.request.AccountRequest;
import com.javaproject.harang.payload.response.TokenResponse;

public interface AuthService {
    TokenResponse userSignIn(AccountRequest accountRequest);
    TokenResponse adminSignIn(AccountRequest accountRequest);
    TokenResponse userRefreshToken(String refreshToken);
    TokenResponse adminRefreshToken(String refreshToken);
}
