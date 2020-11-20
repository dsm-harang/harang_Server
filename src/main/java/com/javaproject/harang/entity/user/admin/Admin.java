package com.javaproject.harang.entity.user.admin;

import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.security.AuthorityType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private String password;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getAge() {
        return null;
    }

    @Override
    public Integer getPhoneNumber() {
        return null;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public String getIntro() {
        return null;
    }

    public AuthorityType getType() {
        return AuthorityType.ADMIN;
    }

    @Override
    public double getAverageScore() {
        return 0;
    }

}
