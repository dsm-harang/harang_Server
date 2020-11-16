package com.javaproject.harang.controller;

import com.javaproject.harang.entity.chat.*;
import com.javaproject.harang.payload.response.chatresponse.ChatMessagesResponseForm;
import com.javaproject.harang.payload.response.chatresponse.ChatRoomForm;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;

import com.javaproject.harang.security.auth.AuthenticationFacade;
import com.javaproject.harang.service.chat.ChatRoomJoinService;
import com.javaproject.harang.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/chat")
    public Map<String, Object> chatHome() {
        Map<String, Object> map = new HashMap<>();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);
        System.out.println();
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(customer);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, customer);
        System.out.println(chatRoomJoins);
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

    @PutMapping("/chat/{chatRoomId}")
    public Integer closeChat(@PathVariable("chatRoomId") Integer chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId).orElseThrow();
        chatRoom.setStatus("close");
        chatRoomRepository.save(chatRoom);
        return 1;
    }

    @PostMapping("/chat/newChat")
    public Integer newChat() {
        Integer chatRoomId = chatRoomJoinService.newRoom();
        return chatRoomId;
    }

    @PostMapping("/chat/addChat")
    public Integer addChat(@RequestParam Integer userId, @RequestParam Integer roomId) {
        return chatRoomJoinService.addRoom(roomId, userId);
    }

    @RequestMapping("/chat/{chatRoomId}")
    public ChatMessagesResponseForm goChat(@PathVariable("chatRoomId") Integer chatRoomId) {

        ChatMessagesResponseForm form = new ChatMessagesResponseForm();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer customer = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();
        List<ChatMessage> messages = chatRoom.getMessages();

        for (ChatMessage c : messages) {
            System.out.println(c.getMessage());
            System.out.println("----------------");
        }
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
            if (!join.getCustomer().getName().equals(customer.getName())) {
                form.setReceiver(join.getCustomer().getName());
                ++cnt;
            }
        }
        if(cnt == 0){
            form.setReceiver("0");
        }
        return form;
    }
}
