package com.javaproject.harang.entity.refresh_token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash(value = "refresh_token")
@AllArgsConstructor
public class RefreshToken {

    @Id
    private final Integer id;

    @Indexed
    private String refreshToken;

    @Indexed
    private Long ttl;

    public RefreshToken update(String refreshToken, Long ttl) {
        this.refreshToken = refreshToken;
        this.ttl = ttl;
        return this;
    }

}