package com.javaproject.harang.service.notice;

import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {
    private final NotifyRepository notifyRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CustomerRepository customerRepository;

    public void addChatNotice(Integer targetName) {
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Chat)
                .content(targetName+"님이 메세지를 초대했습니다.")
                .build());
    }
    public void addScoreNotice(Integer targetName){
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Score)
                .content(targetName+"의 게시물이 마감되었습니다.")
                .build());
    }
    public void addPostNotice(Integer targetName){
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Post)
                .content(targetName+"게시물에 신청이 왔습니다.")
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
