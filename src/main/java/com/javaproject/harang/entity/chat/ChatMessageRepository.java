package com.javaproject.harang.entity.chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessage,Integer> {
//    Optional<ChatMessageForm> findByChatRoomId();
//    Optional<ChatLogForm>


}
