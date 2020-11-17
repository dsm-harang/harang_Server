package com.javaproject.harang.service.admin;

import com.javaproject.harang.payload.response.PostReportResponse;
import com.javaproject.harang.payload.response.UserPageResponse;
import com.javaproject.harang.payload.response.UserReportResponse;

import java.util.List;

public interface AdminService {
    void userDelete(Integer targetId);
    void userPostDelete(Integer postId);
    void userReportDelete(Integer targetId);
    void postReportDelete(Integer postId);
    void score(Integer userId);
    List<PostReportResponse> postReport();
    List<UserReportResponse> userReport();
    UserPageResponse userPage(Integer userId);
}
