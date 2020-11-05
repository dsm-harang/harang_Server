package com.javaproject.harang.entity.Post;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private LocalDateTime createdAt;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime meetTime;

    private String tag;

    private String image;

    private String address;

    private Integer ageLimit;

    private Integer personnel;

    public Post updateFileName(String fileName) {
        this.image = fileName;

        return this;
    }

    public Post updateMeetTime(LocalDateTime meetTime) {
        this.meetTime = meetTime;

        return this;
    }

}
