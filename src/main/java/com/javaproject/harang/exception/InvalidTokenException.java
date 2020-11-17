package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(){
        super(ErrorCode.INVALID_TOKEN);
    }
}
