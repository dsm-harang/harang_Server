package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.chat.ChatRoom;
import com.javaproject.harang.entity.chat.ChatRoomJoin;
import com.javaproject.harang.entity.chat.ChatRoomJoinRepository;
import com.javaproject.harang.entity.chat.ChatRoomRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomJoinNotFound;
import com.javaproject.harang.exception.ChatRoomNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import com.javaproject.harang.service.notice.NotifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final NotifyServiceImpl notifyService;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<ChatRoomJoin> findByUser(Customer customer) {
        return chatRoomJoinRepository.findByCustomer(customer);
    }

    @Transactional
    public Integer newRoom(Integer postId) {
        System.out.println(postId);
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode).
                orElseThrow(UserNotFound::new);
        Post post = postRepository.findById(postId).orElseThrow(ChatRoomNotFound::new);

        try {
            ChatRoom checkChatRoom = chatRoomRepository.findByPostId(postId).get();

            if (checkChatRoom.getPostId().equals(postId)) {
                throw new ChatRoomNotFound();
            } else {
                ChatRoom chatRoom = new ChatRoom();

                chatRoom.setStatus("true");
                chatRoom.setPostId(postId);
                ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
                createRoom(user.getId(), newChatRoom);
                return newChatRoom.getId();
            }
        } catch (NoSuchElementException e) {
            ChatRoom chatRoom = new ChatRoom();

            chatRoom.setStatus("true");
            chatRoom.setPostId(postId);
            ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
            createRoom(user.getId(), newChatRoom);
            return newChatRoom.getId();
        } catch (RuntimeException e) {
            throw new ChatRoomNotFound();
        }
    }

    @Transactional
    public void addRoom(Integer roomId, Integer userId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer token_user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        Customer customer = customerRepository.findById(userId).orElseThrow(UserNotFound::new);
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(UserNotFound::new);
        ChatRoomJoin chatRoomJoin = chatRoomJoinRepository.findByCustomerAndChatRoom(token_user, chatRoom).orElseThrow(ChatRoomJoinNotFound::new);
        try {
            ChatRoomJoin chatRoomJoin1 = chatRoomJoinRepository.findByCustomerAndChatRoom(customer, chatRoom).get();
            if (token_user.equals(chatRoomJoin.getCustomer())) {
                if (customer.equals(chatRoomJoin1.getCustomer())) {
                    throw new UserNotFound();
                } else {
                    notifyService.addChatNotice(chatRoom.getPostId(),userId);
                    createRoom(userId, chatRoom);
                }
            } else throw new UserNotFound();
        } catch (NoSuchElementException e) {
            notifyService.addChatNotice(chatRoom.getPostId(),userId);
            createRoom(userId, chatRoom);
        } catch (RuntimeException e) {
            throw new UserNotFound();
        }
    }

    @Transactional
    public void createRoom(Integer user, ChatRoom chatRoom) {
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin(customerRepository.findById(user).orElseThrow(UserNotFound::new), chatRoom);
        chatRoomJoinRepository.save(chatRoomJoin);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom) {
        return chatRoomJoinRepository.findByChatRoom(chatRoom);
    }

    public String findAnotherUser(ChatRoom chatRoom, String name) {
        List<ChatRoomJoin> chatRoomJoins = findByChatRoom(chatRoom);
        for (ChatRoomJoin chatRoomJoin : chatRoomJoins) {
            if (!name.equals(chatRoomJoin.getCustomer().getName())) {
                return chatRoomJoin.getCustomer().getName();
            }
        }
        return name;
    }
}
