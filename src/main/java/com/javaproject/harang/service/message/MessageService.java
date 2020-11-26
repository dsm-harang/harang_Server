package com.javaproject.harang.service.message;

import com.javaproject.harang.payload.response.MessageListResponse;
import com.javaproject.harang.payload.response.MessageMemberResponse;
import com.javaproject.harang.payload.response.MessageResponse;
import com.javaproject.harang.payload.response.MessageScoreResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MessageService {
    List<MessageResponse> getMessageList(Integer postId);
    List<MessageListResponse> listMyRoom();
    List<MessageScoreResponse> MessageSeeScore(Integer postId);
}
