package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class TokenNotFound extends BusinessException {
    public TokenNotFound() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}
