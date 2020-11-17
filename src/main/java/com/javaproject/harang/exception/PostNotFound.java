package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class PostNotFound extends BusinessException {
    public PostNotFound() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
