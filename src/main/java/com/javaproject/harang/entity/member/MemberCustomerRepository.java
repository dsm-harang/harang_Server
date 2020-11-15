package com.javaproject.harang.entity.member;

import com.javaproject.harang.entity.user.User;

import java.util.List;

public interface MemberCustomerRepository {
    List<MyPostForm> findALLByuserId(Integer postId);
    List<Member> findALLByPostId(int postId);
}
