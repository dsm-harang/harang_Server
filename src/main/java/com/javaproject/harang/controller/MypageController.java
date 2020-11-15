package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.service.mypage.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/{id}")
    public Map<String, Object> SeeOtherPage(@PathVariable Integer Id) {
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
}

