package com.javaproject.harang.entity.score;

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
    @Column(name="score",nullable = false,columnDefinition = "0")
    private Integer score;

    private LocalDateTime scoreAt;

    private String scoreComment;

    private Integer scoreTargetId;


}
