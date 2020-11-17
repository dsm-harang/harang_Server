package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class UserNotFound extends BusinessException {
    public UserNotFound() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
