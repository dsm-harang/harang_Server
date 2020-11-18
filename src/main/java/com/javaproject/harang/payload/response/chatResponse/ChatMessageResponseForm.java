package com.javaproject.harang.payload.response.chatresponse;

import com.javaproject.harang.entity.chat.ChatRoom;
import com.javaproject.harang.entity.user.customer.Customer;

import java.time.LocalDateTime;

public class ChatMessageResponseForm {
    private Integer id;
    private String message;
    private LocalDateTime time;
    private ChatRoom chatRoom;
    private Customer writer;

    public ChatMessageResponseForm() {}
    public ChatMessageResponseForm(Integer id, String message, LocalDateTime time, ChatRoom chatRoom, Customer writer) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.chatRoom = chatRoom;
        this.writer = writer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Customer getWriter() {
        return writer;
    }

    public void setWriter(Customer writer) {
        this.writer = writer;
    }
}
