package com.javaproject.harang.service.message;

import com.javaproject.harang.payload.response.MessageMemberResponse;
import com.javaproject.harang.payload.response.MessageResponse;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getMessageList(String roomId, Integer postId);
}
