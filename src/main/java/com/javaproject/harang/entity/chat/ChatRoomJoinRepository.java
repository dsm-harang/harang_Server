package com.javaproject.harang.entity.chat;

import com.javaproject.harang.entity.user.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomJoinRepository extends CrudRepository<ChatRoomJoin,Integer> {
     List<ChatRoomJoin> findByCustomer(Customer customer);
     List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);
     Optional<ChatRoomJoin> findByCustomerAndChatRoom(Customer customer, ChatRoom chatRoom);

}
