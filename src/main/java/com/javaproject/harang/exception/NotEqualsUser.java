package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class NotEqualsUser extends BusinessException {
    public NotEqualsUser() {
        super(ErrorCode.NOT_EQUALS_USER);
    }
}