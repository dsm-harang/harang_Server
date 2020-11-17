package com.javaproject.harang.entity.chat;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ChatRoomRepository extends CrudRepository<ChatRoom,Integer> {
    Optional<ChatRoom> findById(Integer id);
    Optional<ChatRoom> findByPostId(Integer PostId);

}
