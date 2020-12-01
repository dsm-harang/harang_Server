package com.javaproject.harang.service.room;

public interface MessageRoomService {
    void createMessageRoom(Integer userId, Integer postId);
    void    addMessageRoom(Integer userId, Integer postId);
    void closeRoom(Integer postId);
}
