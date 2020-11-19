package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.payload.response.MySeePageResponse;
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

    @PutMapping()
    public Map<String, Object> UpdateMyPage(@RequestParam String intro,
                                            @RequestParam MultipartFile image) {
        return mypageService.UpdateMyPage(
                MyPageUpdateRequest.builder()
                        .intro(intro)
                        .imagePath(image)
                        .build()
        );
    }

    @GetMapping("/score/{Id}")
    public ScoreResponse GetScore(@PathVariable("Id") Integer Id) {
        return mypageService.GetScore(Id);
    }

    @PostMapping("/score")
    public void SendScore(@RequestParam Integer score,
                                         @RequestParam Integer postId,
                                         @RequestParam String scoreContent,
                                         @RequestParam Integer scoreTargetId) {
       mypageService.SendScore(postId, score, scoreContent, scoreTargetId);
    }

    @GetMapping("/scoreList/{postId}")
    public ListScoreResponse ListScore(@PathVariable("postId") Integer postId) {
        return mypageService.ListScore(postId);
    }

    @GetMapping("/post")
    public MySeePageResponse MyPost() {
        return mypageService.MyPost();
    }



}

