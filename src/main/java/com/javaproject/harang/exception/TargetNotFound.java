package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class TargetNotFound extends BusinessException {
    public TargetNotFound() {
        super(ErrorCode.TARGET_NOT_FOUND);
    }
}
