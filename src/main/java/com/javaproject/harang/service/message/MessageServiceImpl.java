package com.javaproject.harang.service.message;

import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.message.Message;
import com.javaproject.harang.entity.message.MessageRoom;
import com.javaproject.harang.entity.message.MessageRoomJoin;
import com.javaproject.harang.entity.message.repository.MessageRepository;
import com.javaproject.harang.entity.message.repository.MessageRoomJoinRepository;
import com.javaproject.harang.entity.message.repository.MessageRoomRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.ChatRoomNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.*;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import com.javaproject.harang.service.room.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final CustomerRepository customerRepository;
    private final MessageRoomJoinRepository messageRoomJoinRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MessageRoomRepository messageRoomRepository;

    @Override
    public List<MessageResponse> getMessageList(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);


        MessageRoom messageRoom = messageRoomRepository.findByPostId(postId).orElseThrow();
        List<Message> messageResponses = messageRepository.findBySenderIdAndRoomId(user.getId(), messageRoom.getId());

        List<MessageResponse> messageResponseList = new ArrayList<>();
        for (Message message : messageResponses) {
            Customer customer = customerRepository.findById(message.getSenderId())
                    .orElseThrow(UserNotFound::new);

            messageResponseList.add(
                    MessageResponse.builder()
                            .userId(customer.getId())
                            .userName(customer.getName())
                            .message(message.getContent())
                            .isMine(user.getId().equals(message.getSenderId()))
                            .build()
            );

        }

        return messageResponseList;
    }

    public List<MessageListResponse> listMyRoom() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        List<MessageRoomResponse> messageRoomResponses = new ArrayList<>();
        for (MessageRoomJoin messageRoomJoin : messageRoomJoinRepository.findAllByUserId(user.getId())) {
            messageRoomResponses.add(
                    MessageRoomResponse.builder()
                            .postId(messageRoomJoin.getPostId())
                            .roomId(messageRoomJoin.getRoomId())
                            .roomStatus(messageRoomRepository.findById(messageRoomJoin.getRoomId())
                                    .orElseThrow(ChatRoomNotFound::new).getRoomStatus())
                            .build()
            );
        }
        List<MessageListResponse> messageListResponses = new ArrayList<>();
        messageListResponses.add(
                MessageListResponse.builder()
                        .messageRoom(messageRoomResponses)
                        .userId(user.getId())
                        .userName(user.getName())
                        .build()
        );
        return messageListResponses;
    }

    @Override
    public List<MessageScoreResponse> MessageSeeScore(Integer postId) {
        List<Member> memberList = memberRepository.findAllByPostId(postId);

        List<MessageScoreResponse> messageMember = new ArrayList<>();
        for (Member member : memberList) {
            Customer customer = customerRepository.findById(member.getUserId())
                    .orElseThrow(UserNotFound::new);
            messageMember.add(
                    MessageScoreResponse.builder()
                    .Score(customer.getAverageScore())
                    .userName(customer.getName())
                    .userId(customer.getId())
                    .build()
            );
        }
        return messageMember;
    }

}
