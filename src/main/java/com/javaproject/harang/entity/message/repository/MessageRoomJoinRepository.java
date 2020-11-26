package com.javaproject.harang.entity.message.repository;

import com.javaproject.harang.entity.message.MessageRoomJoin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRoomJoinRepository extends CrudRepository<MessageRoomJoin, Integer> {
    List<MessageRoomJoin> findAllByUserId(Integer userId);
    Optional<MessageRoomJoin> findByUserIdAndRoomId(Integer userId,Integer roomId);
    Optional<MessageRoomJoin> findByUserIdAndPostId(Integer userId,Integer postId);

}
