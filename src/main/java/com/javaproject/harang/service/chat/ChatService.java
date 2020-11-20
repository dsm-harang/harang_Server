package com.javaproject.harang.service.chat;


import com.javaproject.harang.payload.response.chatResponse.ChatMessagesResponseForm;

import java.util.List;
import java.util.Map;

public interface ChatService {

    Map<String, Object> chatHome();
    void closeChat(Integer chatRoomId);
    List<ChatMessagesResponseForm> goChat(Integer chatRoomId);
}
