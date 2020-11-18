package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ScoreNotFound extends BusinessException {
    public ScoreNotFound() {
        super(ErrorCode.SCORE_NOT_FOUND);
    }
}

