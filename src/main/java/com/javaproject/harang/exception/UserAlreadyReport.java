package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class UserAlreadyReport extends BusinessException {
    public UserAlreadyReport() {
        super(ErrorCode.USER_ALREADY_REPORT);
    }
}
