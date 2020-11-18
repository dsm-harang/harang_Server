package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class AdminNotFound extends BusinessException {
    public AdminNotFound() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}
