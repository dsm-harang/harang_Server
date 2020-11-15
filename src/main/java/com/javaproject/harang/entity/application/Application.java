package com.javaproject.harang.entity.application;

import com.javaproject.harang.entity.application.eunm.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postId;

    private Integer userId;

    private Integer targetId;

    private LocalDateTime appliedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Application accept() {
        this.status = Status.COMPLETION;

        return this;
    }
}
