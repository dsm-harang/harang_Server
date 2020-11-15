package com.javaproject.harang.service.mypage;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;

import java.util.Map;

public interface MypageService {
    Map<String, Object> SeeMyPage();
    Map<String, Object> SeeOtherPage(Integer Id);
    Map<String, Object> UpdateMyPage(MyPageUpdateRequest myPageUpdateRequest);

}
