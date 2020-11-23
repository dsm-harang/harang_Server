package com.javaproject.harang.entity.message.repository;

import com.javaproject.harang.entity.message.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findBySenderIdAndRoomId(Integer senderId, String roomId);
}
