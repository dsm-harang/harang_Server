package com.javaproject.harang.entity.score;

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
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //이게 나다
//    private String scoreName;
    private Integer userId;

    private Integer score;

    private LocalDateTime score_at;

    private String score_comment;
    //이게 너다.
    private Integer score_target_id;


}
