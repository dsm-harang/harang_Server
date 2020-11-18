package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class UserAlreadyException extends BusinessException {
    public UserAlreadyException() {
        super(ErrorCode.USER_ALREADY_EXCEPTION);
    }
}
