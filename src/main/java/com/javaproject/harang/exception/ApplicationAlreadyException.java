package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ApplicationAlreadyException extends BusinessException {
    public ApplicationAlreadyException() {
        super(ErrorCode.APPLICATION_ALREADY_EXCEPTION);
    }
}
