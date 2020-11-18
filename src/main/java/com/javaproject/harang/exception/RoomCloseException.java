package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class RoomCloseException extends BusinessException {
    public RoomCloseException() {
        super(ErrorCode.ROOM_CLOSE_EXCEPTION);
    }
}

