package com.javaproject.harang.entity.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {
    @Query("SELECT c FROM ChatMessage c WHERE c.chatRoom=:chatRoom")
    List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);
}
