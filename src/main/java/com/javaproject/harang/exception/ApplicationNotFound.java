package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ApplicationNotFound extends BusinessException {
    public ApplicationNotFound() {
        super(ErrorCode.APPLICATION_NOT_FOUND);
    }
}
