package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class WriterNotFound extends BusinessException {
    public WriterNotFound() {
        super(ErrorCode.WRITER_NOT_FOUND);
    }
}
