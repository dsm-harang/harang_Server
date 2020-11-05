package com.javaproject.harang.service.auth;

import com.javaproject.harang.entity.refresh_token.RefreshToken;
import com.javaproject.harang.entity.refresh_token.RefreshTokenRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.admin.AdminRepository;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ExpiredTokenException;
import com.javaproject.harang.exception.UserNotFoundException;
import com.javaproject.harang.payload.request.AccountRequest;
import com.javaproject.harang.payload.response.TokenResponse;
import com.javaproject.harang.security.AuthorityType;
import com.javaproject.harang.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExp;

    @Value("${auth.jwt.prefix}")
    private String tokenType;

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse userSignIn(AccountRequest request) {
        return customerRepository.findByUserId(request.getUserId())
                .filter(customer -> passwordEncoder.matches(request.getPassword(), customer.getPassword()))
                .map(User::getId)
                .map(id -> {
                    String refreshToken = tokenProvider.generateRefreshToken(id, AuthorityType.USER);
                    return new RefreshToken(id, refreshToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String accessToken = tokenProvider.generateAccessToken(refreshToken.getId(), AuthorityType.USER);
                    return new TokenResponse(accessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(UserNotFoundException::new);

    }

    @Override
    public TokenResponse adminSignIn(AccountRequest accountRequest) {
        return adminRepository.findByUserId(accountRequest.getUserId())
                .filter(customer -> passwordEncoder.matches(accountRequest.getPassword(), customer.getPassword()))
                .map(User::getId)
                .map(id -> {
                    String refreshToken = tokenProvider.generateRefreshToken(id, AuthorityType.ADMIN);
                    return new RefreshToken(id, refreshToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String accessToken = tokenProvider.generateAccessToken(refreshToken.getId(), AuthorityType.ADMIN);
                    return new TokenResponse(accessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public TokenResponse userRefreshToken(String receivedToken) {
        if (!tokenProvider.isRefreshToken(receivedToken))
            throw new RuntimeException();

        return refreshTokenRepository.findByRefreshToken(receivedToken)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateRefreshToken(refreshToken.getId(), AuthorityType.USER);
                    return refreshToken.update(generatedAccessToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateAccessToken(refreshToken.getId(), AuthorityType.USER);
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(ExpiredTokenException::new);
    }

    @Override
    public TokenResponse adminRefreshToken(String receivedToken) {
        if (!tokenProvider.isRefreshToken(receivedToken))
            throw new RuntimeException();

        return refreshTokenRepository.findByRefreshToken(receivedToken)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateRefreshToken(refreshToken.getId(), AuthorityType.ADMIN);
                    return refreshToken.update(generatedAccessToken, refreshExp);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken = tokenProvider.generateAccessToken(refreshToken.getId(), AuthorityType.ADMIN);
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(ExpiredTokenException::new);
    }

}
