package com.javaproject.harang.service.message;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.message.Message;
import com.javaproject.harang.entity.message.repository.MessageRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.MessageMemberResponse;
import com.javaproject.harang.payload.response.MessageResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final CustomerRepository customerRepository;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public List<MessageResponse> getMessageList(String roomId, Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<Member> memberList = memberRepository.findAllByPostId(postId);

        List<MessageMemberResponse> messageMember = new ArrayList<>();
        for (Member member : memberList) {
            Customer customer = customerRepository.findById(member.getUserId())
                    .orElseThrow(UserNotFound::new);

            File file = new File(customer.getImagePath());
            messageMember.add(
                    MessageMemberResponse.builder()
                        .score(customer.getAverageScore())
                        .userName(customer.getName())
                        .imageName(file.getName())
                        .build()
            );
        }

        List<Message> messageResponses = messageRepository.findBySenderIdAndRoomId(user.getId(), roomId);

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
        messageResponseList.add(
                MessageResponse.builder()
                    .messageMember(messageMember)
                    .build()
        );

        return messageResponseList;
    }

}
