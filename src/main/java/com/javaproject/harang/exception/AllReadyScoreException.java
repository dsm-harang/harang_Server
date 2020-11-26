package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class AllReadyScoreException extends BusinessException {
    public AllReadyScoreException() {
        super(ErrorCode.ALL_READY_SCORE);
    }
}

