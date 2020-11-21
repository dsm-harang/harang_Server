package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.MyPostListResponse;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.service.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/{Id}")
    public Map<String, Object> SeeOtherPage(@PathVariable("Id") Integer Id) {
        return mypageService.SeeOtherPage(Id);
    }

    @GetMapping()
    public Map<String, Object> SeeMyPage() {
        return mypageService.SeeMyPage();
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
    public ListScoreResponse ListScore(@PathVariable("postId") Integer postId) {
        return mypageService.ListScore(postId);
    }

    @GetMapping("/post")
    public List<MyPostListResponse> myPost() {
        return mypageService.myPost();
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

