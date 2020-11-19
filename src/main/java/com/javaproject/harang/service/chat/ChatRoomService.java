//package com.javaproject.harang.service.chat;
//
//import com.javaproject.harang.entity.chat.*;
//import com.javaproject.harang.payload.response.chatresponse.ChatMessageForm;
//import com.javaproject.harang.entity.user.customer.Customer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
//    private final ChatRoomRepository chatRoomRepository;
//    private final ChatRoomJoinService chatRoomJoinService;
//    @Transactional(readOnly = true)
//    public Optional<ChatRoom> findById(Integer id) {
//        return chatRoomRepository.findById(id);
//    }
//
//
//    public List<ChatMessageForm> setting(List<ChatRoomJoin> chatRoomJoins, Customer user) {
//        List<ChatMessageForm> chatRooms = new ArrayList<>();
//        for(ChatRoomJoin tmp : chatRoomJoins){
//            ChatMessageForm chatRoomForm = new ChatMessageForm();
//            ChatRoom chatRoom = tmp.getChatRoom();
//            chatRoomForm.setId(chatRoom.getId());
//            if(chatRoom.getMessages().size() == 0) {
//                    chatRoomForm.makeChatRoomForm("null",chatRoomJoinService.findAnotherUser(chatRoom, user.getName()), LocalDateTime.now());
//            }
//            else {
//                Collections.sort(chatRoom.getMessages(), new Comparator<ChatMessage>() {
//                    @Override
//                    public int compare(ChatMessage c1, ChatMessage c2) {
//                        if(c1.getTime().isAfter(c2.getTime())){
//                            return -1;
//                        }
//                        else{
//                            return 1;
//                        }
//                    }
//                });
//                ChatMessage lastMessage = chatRoom.getMessages().get(0);
//                chatRoomForm.makeChatRoomForm(lastMessage.getMessage(),chatRoomJoinService.findAnotherUser(chatRoom, user.getName()),lastMessage.getTime());
//            }
//            chatRooms.add(chatRoomForm);
//        }
//        Collections.sort(chatRooms, new Comparator<ChatRoomForm>() {
//            @Override
//            public int compare(ChatRoomForm c1, ChatRoomForm c2) {
//                if(c1.getTime().isAfter(c2.getTime())){
//                    return -1;
//                }
//                else{
//                    return 1;
//                }
//            }
//        });
//        return chatRooms;
//    }
//}
