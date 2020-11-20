package com.javaproject.harang.entity.Post;

import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getUserId() {
        return user.getId();
    }

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
