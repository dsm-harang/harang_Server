package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.AllPostListResponse;
import com.javaproject.harang.payload.response.PageInfoResponse;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MyPageService mypageService;

    @GetMapping("/{id}")
    public PageInfoResponse getOtherPage(@PathVariable Integer id) {
        return mypageService.getOtherPage(id);
    }

    @GetMapping()
    public PageInfoResponse getMyPage() {
        return mypageService.getMyPage();
    }


    @GetMapping("/score/{targetId}")
    public List<ScoreResponse> getTargetScore(@PathVariable Integer targetId) {
        return mypageService.getTargetScore(targetId);
    }

    @GetMapping("/score")
    public List<ScoreResponse> getMyScore() {
        return mypageService.getMyScore();
    }

    @GetMapping("/scoreList/{postId}")
    public List<ListScoreResponse> listScore(@PathVariable("postId") Integer postId) {
        return mypageService.listScore(postId);
    }

    @GetMapping("/post")
    public List<AllPostListResponse> myPost() {
        return mypageService.myPost();
    }

    @GetMapping("/post/{targetId}")
    public List<AllPostListResponse> targetPost(@PathVariable Integer targetId) {
        return mypageService.targetPost(targetId);
    }

    @PostMapping("/score/{targetId}")
    public void SendScore(@PathVariable Integer targetId, @RequestBody SendScoreRequest sendScoreRequest) {
       mypageService.SendScore(targetId, sendScoreRequest);
    }

    @PutMapping()
    public void UpdateMyPage(@RequestParam String intro,
                             @RequestParam MultipartFile image) {
        mypageService.updateMyPage(
                MyPageUpdateRequest.builder()
                        .intro(intro)
                        .imagePath(image)
                        .build()
        );
    }

}

