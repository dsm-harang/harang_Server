package com.javaproject.harang.entity.member;

import com.javaproject.harang.payload.response.MyPostResponse;

import java.util.List;

public interface MemberCustomerRepository {
    List<MyPostResponse> findALLByuserId(Integer postId);
    List<Member> findALLByPostId(int postId);
}
