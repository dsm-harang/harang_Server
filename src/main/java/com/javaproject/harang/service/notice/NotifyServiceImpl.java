package com.javaproject.harang.service.notice;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {
    private final NotifyRepository notifyRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;
    private final MemberRepository memberRepository;

    public void addChatNotice(Integer postId, Integer userId) {
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .postId(postId)
                .type(NotifyType.Chat)
                .content(userId + "님에게 메세지를 초대가왔습니다.")
                .build());
    }

    public void addScoreNotice(Integer postId) {
        List<Member> member = memberRepository.findALLByPostId(postId);
        member.forEach(m -> {
                    if (!notifyRepository.findByUserIdAndPostIdAndType(m.getUserId(), postId, NotifyType.Score).isPresent()) {
                        notifyRepository.save(Notify.builder()
                                .createdAt(LocalDateTime.now())
                                .userId(m.getUserId())
                                .postId(postId)
                                .type(NotifyType.Score)
                                .content(postId + "의 게시물이 마감되었습니다.")
                                .build());
                    }
                }
        );

    }

    public void addPostNotice(Integer postId, Integer userId) {
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .postId(postId)
                .type(NotifyType.Post)
                .content(postId + "게시물에 신청이 왔습니다.")
                .build());
    }

    @Override
    public NotifyResponse MyNotify() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        List<Notify> notify = notifyRepository.findAllByUserId(user.getId());

        return new NotifyResponse(notify);
    }
}
