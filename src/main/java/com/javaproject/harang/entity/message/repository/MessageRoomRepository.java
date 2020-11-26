package com.javaproject.harang.entity.message.repository;

import com.javaproject.harang.entity.message.MessageRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRoomRepository extends CrudRepository<MessageRoom, Integer> {
    Optional<MessageRoom> findByPostId(Integer postId);
//    boolean existsByIdAndSenderId(Integer roomId, Integer senderId);


}
