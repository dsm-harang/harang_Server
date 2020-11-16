package com.javaproject.harang.controller;

import com.javaproject.harang.payload.response.PostReportResponse;
import com.javaproject.harang.payload.response.UserPageResponse;
import com.javaproject.harang.payload.response.UserReportResponse;
import com.javaproject.harang.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/user/{userId}")
    public void userDelete(@PathVariable Integer userId){
        adminService.userDelete(userId);
    }

    @DeleteMapping("post/{postId}")
    public void userPostDelete(@PathVariable Integer postId) {
        adminService.userPostDelete(postId);
    }

    @DeleteMapping("report/user/{userId}")
    private void userReportDelete(@PathVariable Integer userId) {
        adminService.userReportDelete(userId);
    }

    @DeleteMapping("report/post/{postId}")
    private void postReportDelete(@PathVariable Integer postId) {
        adminService.postReportDelete(postId);
    }

    @DeleteMapping("score/{userId}")
    private void score(@PathVariable Integer userId) {
        adminService.score(userId);
    }

    @GetMapping("post")
    public List<PostReportResponse> postReport() {
        return adminService.postReport();
    }

    @GetMapping("user")
    public List<UserReportResponse> userReport() {
        return adminService.userReport();
    }

    @GetMapping("/{userId}")
    public UserPageResponse userPage(@PathVariable Integer userId){
        return adminService.userPage(userId);
    }
}
