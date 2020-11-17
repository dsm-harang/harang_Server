package com.javaproject.harang.payload.response.chatresponse;

import com.javaproject.harang.entity.chat.ChatMessage;

import java.util.List;

public class ChatMessagesResponseForm {
    private String receiver;
    private String userName;
    private int userId;
    private int chatRoomId;
    private List<ChatMessage> messages;

    public ChatMessagesResponseForm() {}
    public ChatMessagesResponseForm(String receiver, String userName, int userId, int chatRoomId, List<ChatMessage> messages) {
        this.receiver = receiver;
        this.userName = userName;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.messages = messages;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
