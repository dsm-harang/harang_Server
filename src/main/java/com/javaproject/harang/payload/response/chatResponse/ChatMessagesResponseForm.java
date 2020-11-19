package com.javaproject.harang.payload.response.chatResponse;

import com.javaproject.harang.entity.chat.ChatMessage;

import java.util.List;

public class ChatMessagesResponseForm {
    private String userName;
    private int userId;
    private int chatRoomId;
    private List<ChatMessage> messages;

    public ChatMessagesResponseForm() {}
    public ChatMessagesResponseForm( String userName, int userId, int chatRoomId, List<ChatMessage> messages) {
        this.userName = userName;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.messages = messages;
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
