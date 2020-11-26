package com.javaproject.harang.payload.response;

import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class NotifyResponse {
    private Integer notifyId;
    private Integer userUuid;
    private String content;
    private Integer postId;
    private LocalDateTime creatdAt;
    private NotifyType notifyType;
}

