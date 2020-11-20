package com.javaproject.harang.entity.user;

import com.javaproject.harang.security.AuthorityType;

public interface User {
    Integer getId();
    String getUserId();
    String getPassword();
    String getName();
    Integer getAge();
    Integer getPhoneNumber();
    String getImagePath();
    String getIntro();
    AuthorityType getType();
    double getAverageScore();
}
