package com.javaproject.harang.entity.chat;

import com.javaproject.harang.entity.user.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomJoinRepository extends CrudRepository<ChatRoomJoin,Integer> {
     List<ChatRoomJoin> findByCustomer(Customer customer);
     List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);

}
