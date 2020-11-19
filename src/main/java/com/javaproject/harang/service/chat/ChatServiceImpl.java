package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.chat.*;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomJoinNotFound;
import com.javaproject.harang.exception.ChatRoomNotFound;
import com.javaproject.harang.exception.NotEqualsUser;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.chatResponse.ChatMessagesResponseForm;
import com.javaproject.harang.payload.response.chatResponse.ChatRoomForm;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Override
    public Map<String, Object> chatHome() {
        Map<String, Object> map = new HashMap<>();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(customer);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, customer);
        map.put("chatRooms", chatRooms);
        if (customer == null) {
            map.put("userName", "");
            map.put("userId", 0);
        } else {
            map.put("userName", customer.getName());
            map.put("userId", customer.getId());
        }
        return map;
    }

    @Override
    public void closeChat(Integer chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId).orElseThrow();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode).get();
        System.out.println(customer.getId());
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoom).orElseThrow(ChatRoomJoinNotFound::new);
        Member member = memberRepository.findByUserIdAndPostId(customer.getId(),chatRoomId).orElseThrow(UserNotFound::new);
        if (customer.equals(chatRoomJoin.getCustomer())) {
            chatRoom.setStatus("close");
            chatRoomRepository.save(chatRoom);
        }
    }

    @Override
    public ChatMessagesResponseForm goChat(Integer chatRoomId) {

        ChatMessagesResponseForm form = new ChatMessagesResponseForm();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFound::new);
        List<ChatMessage> messages = chatRoom.getMessages();
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoom)
                .orElseThrow(ChatRoomJoinNotFound::new);
        if (customer.equals(chatRoomJoin.getCustomer())) {
            Collections.sort(messages, (t1, t2) -> {
                if (t1.getId() > t2.getId()) return -1;
                else return 1;
            });
            if (customer == null) {
                form.setUserName("");
                form.setUserId(0);
            } else {
                form.setUserName(customer.getName());
                form.setUserId(customer.getId());
            }
            List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
            form.setMessages(messages);
            form.setChatRoomId(chatRoom.getId());
            int cnt = 0;
            for (ChatRoomJoin join : list) {
                if (join.getCustomer().getName().equals(customer.getName())) {
                    ++cnt;
                }
            }
            if (cnt == 0) {
                throw new UserNotFound();
            }
        } else {
            throw new NotEqualsUser();
        }
        return form;
    }
}