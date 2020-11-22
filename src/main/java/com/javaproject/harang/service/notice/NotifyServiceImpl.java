package com.javaproject.harang.service.notice;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.PostNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final PostRepository postRepository;
    private final NotifyRepository notifyRepository;
    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;

    private final AuthenticationFacade authenticationFacade;

    public void addChatNotice(Integer postId, Integer userId) {
        User user = customerRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(userId)
                .postId(postId)
                .type(NotifyType.Chat)
                .content(user.getName() + "님에게 메세지를 초대가왔습니다.")
                .build());
    }

    public void addScoreNotice(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        List<Member> member = memberRepository.findALLByPostId(postId);
        member.forEach(m -> {
                    if (!notifyRepository.findByUserIdAndPostIdAndType(m.getUserId(), postId, NotifyType.Score).isPresent()) {
                        notifyRepository.save(Notify.builder()
                                .createdAt(LocalDateTime.now())
                                .userId(m.getUserId())
                                .postId(postId)
                                .type(NotifyType.Score)
                                .content(post.getTitle() + "의 게시물이 마감되었습니다.")
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
    public List<NotifyResponse> myNotify() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<Notify> notifyList = notifyRepository.findAllByUserId(user.getId());

        List<NotifyResponse> notifyResponses = new ArrayList<>();
        for (Notify notify : notifyList) {
            notifyResponses.add(
                    NotifyResponse.builder()
                        .notifyId(notify.getId())
                        .userUuid(user.getId())
                        .postId(notify.getPostId())
                        .content(notify.getContent())
                        .creatdAt(notify.getCreatedAt())
                        .build()
            );
        }

        return notifyResponses;
    }
}
