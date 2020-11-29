package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class NotifyNotFound extends BusinessException {
    public NotifyNotFound() {
        super(ErrorCode.NOTIFY_NOT_FOUND);
    }
}

