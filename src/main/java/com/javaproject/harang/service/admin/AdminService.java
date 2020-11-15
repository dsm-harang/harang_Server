package com.javaproject.harang.service.admin;

import com.javaproject.harang.payload.response.PostReportResponse;
import com.javaproject.harang.payload.response.UserReportResponse;

import java.util.List;

public interface AdminService {
    void userDelete(Integer userId);
    void userPostDelete(Integer postId);
    void userReportDelete(Integer userId);
    void postReportDelete(Integer postId);
    void score(Integer userId);
    List<PostReportResponse> postReport();
    List<UserReportResponse> userReport();
}
