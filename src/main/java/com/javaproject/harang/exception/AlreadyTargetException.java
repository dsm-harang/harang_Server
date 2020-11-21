package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class AlreadyTargetException extends BusinessException {
    public AlreadyTargetException() {
        super(ErrorCode.ALREADY_TARGET_EXCEPTION);
    }
}
