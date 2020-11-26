package com.javaproject.harang.entity.message.repository;

import com.javaproject.harang.entity.message.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findBySenderIdAndRoomId(Integer senderId, Integer roomId);
    boolean existsByRoomIdAndSenderId(Integer roomId, Integer senderId);
    Optional<Message> findByRoomIdAndSenderIdNot(Integer roomId, Integer senderId);

}
