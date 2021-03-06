package com.javaproject.harang.entity.notify;

import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String content;

    private Integer postId;

    private LocalDateTime createdAt;

//    private String name;

    private NotifyType type;

}

