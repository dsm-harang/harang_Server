package com.javaproject.harang.entity.score;

import com.javaproject.harang.entity.message.MessageRoom;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer score;

    private LocalDateTime scoreAt;

    private String scoreComment;

    private Integer scoreTargetId;

    private Integer postId;

    public Score updateScore(Integer score,LocalDateTime scoreAt,String scoreComment) {
        this.score = score;
        this.scoreAt = scoreAt;
        this.scoreComment = scoreComment;

        return this;
    }

}
