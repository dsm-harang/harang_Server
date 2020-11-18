package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class MemberNotFound  extends BusinessException {
    public MemberNotFound() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
