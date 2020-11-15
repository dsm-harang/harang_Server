package com.javaproject.harang.service.notice;

import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotifyService {
    private final NotifyRepository notifyRepository;

    @MessageMapping
    public void addChatNotice(Integer targetName) {
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Chat)
                .content("님이 메세지를 초대했습니다.")
                .build());
    }
    public void addScoreNotice(Integer targetName){
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Score)
                .content("별점.")
                .build());
    }
    public void addPostNotice(Integer targetName){
        notifyRepository.save(Notify.builder()
                .createdAt(LocalDateTime.now())
                .userId(targetName)
                .Type(NotifyType.Post)
                .content("게시물")
                .build());
    }
}
