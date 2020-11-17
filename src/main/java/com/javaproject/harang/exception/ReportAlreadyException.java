package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ReportAlreadyException extends BusinessException {
    public ReportAlreadyException() {
        super(ErrorCode.REPORT_ALREADY_EXCEPTION);
    }
}
