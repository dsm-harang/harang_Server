package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class MemberAlreadyIncludeException extends BusinessException {
    public MemberAlreadyIncludeException() {
        super(ErrorCode.MEMBER_ALREADY_INCLUDE_EXCEPTION);
    }
}
