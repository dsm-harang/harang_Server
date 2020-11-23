package com.javaproject.harang.service.room;

import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.message.MessageRoom;
import com.javaproject.harang.entity.message.repository.MessageRoomRepository;
import com.javaproject.harang.exception.PostNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceImpl implements  MessageRoomService{

    private final PostRepository postRepository;
    private final MessageRoomRepository messageRoomRepository;

    @Override
    public void createMessageRoom(Integer userId, Integer postId) {
        String roomId = UUID.randomUUID().toString();

        messageRoomRepository.save(
                MessageRoom.builder()
                    .roomId(roomId)
                    .userId(userId)
                    .postId(postId)
                    .build()
        );
    }

    @Override
    public void addMessageRoom(Integer userId, Integer postId) {
        MessageRoom messageRoom = messageRoomRepository.findByPostId(postId)
                .orElseThrow(PostNotFound::new);

        messageRoomRepository.save(
                MessageRoom.builder()
                    .userId(userId)
                    .roomId(messageRoom.getRoomId())
                    .postId(postId)
                    .build()
        );
    }
}
