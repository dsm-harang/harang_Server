package com.javaproject.harang.exception;

import com.javaproject.harang.error.BusinessException;
import com.javaproject.harang.error.ErrorCode;

public class ChatRoomJoinNotFound extends BusinessException {
    public ChatRoomJoinNotFound() {
        super(ErrorCode.CHATROOM_JOIN_NOT_FOUND);
    }
}
