package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.ChatRoom;
import com.javaproject.harang.entity.chat.ChatRoomJoin;
import com.javaproject.harang.entity.chat.ChatRoomJoinRepository;
import com.javaproject.harang.entity.chat.ChatRoomRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import com.javaproject.harang.service.notice.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final CustomerRepository usersService;
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final NotifyService notifyService;
    @Transactional(readOnly = true)
    public List<ChatRoomJoin> findByUser(Customer customer) {
        return chatRoomJoinRepository.findByCustomer(customer);
    }

    @Transactional(readOnly = true)
    public Integer check(Integer userId){

        Customer userFirst = usersService.findById(userId).orElseThrow();
        List<ChatRoomJoin> listFirst = chatRoomJoinRepository.findByCustomer(userFirst);
        Set<ChatRoom> setFirst = new HashSet<>();
        for(ChatRoomJoin chatRoomJoin : listFirst){
            setFirst.add(chatRoomJoin.getChatRoom());
        }
        return 0;
    }
    @Transactional
    public Integer newRoom() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Integer ret = check(user.getId());
        if(ret != 0){
            //이미 존재하는 방이면 해당 방 번호 리턴
            return ret;
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setStatus("true");
        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
        createRoom(user.getId(),newChatRoom);
        return newChatRoom.getId();
    }
    @Transactional
    public Integer addRoom(Integer roomId,Integer userId){
//        Integer receiptCode = authenticationFacade.getReceiptCode();
//        Customer token_user = customerRepository.findById(receiptCode)
//                .orElseThrow(RuntimeException::new);
        Customer user= customerRepository.findById(userId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow();

        notifyService.addChatNotice(user.getId());
        createRoom(user.getId(),chatRoom);

        return 1;
    }
    @Transactional
    public void createRoom(Integer user, ChatRoom chatRoom){
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin(customerRepository.findById(user).orElseThrow(),chatRoom);
        chatRoomJoinRepository.save(chatRoomJoin);
    }
    @Transactional(readOnly = true)
    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom) {
        return chatRoomJoinRepository.findByChatRoom(chatRoom);
    }
    @Transactional
    public void delete(ChatRoomJoin chatRoomJoin) {
        chatRoomJoinRepository.delete(chatRoomJoin);
    }

    public String findAnotherUser(ChatRoom chatRoom, String name) {
        List<ChatRoomJoin> chatRoomJoins = findByChatRoom(chatRoom);
        for(ChatRoomJoin chatRoomJoin : chatRoomJoins){
            if(!name.equals(chatRoomJoin.getCustomer().getName())){
                return chatRoomJoin.getCustomer().getName();
            }
        }
        return name;
    }
}
