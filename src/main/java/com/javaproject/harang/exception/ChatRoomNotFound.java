package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ChatRoomNotFound extends BusinessException {
    public ChatRoomNotFound() {
        super(ErrorCode.CHATROOM_NOT_FOUND);
    }
}