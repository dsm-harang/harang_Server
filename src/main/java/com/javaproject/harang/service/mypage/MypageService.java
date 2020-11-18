package com.javaproject.harang.service.mypage;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.payload.response.MySeePageResponse;
import com.javaproject.harang.payload.response.ScoreResponse;

import java.util.List;
import java.util.Map;

public interface MypageService {
    Map<String, Object> SeeMyPage();

    Map<String, Object> SeeOtherPage(Integer Id);

    Map<String, Object> UpdateMyPage(MyPageUpdateRequest myPageUpdateRequest);

    ScoreResponse GetScore(Integer Id);

    Map<String, Object> SendScore(Integer postId, Integer score, String scoreContent, Integer scoreTargetId);

    ListScoreResponse ListScore(Integer postId);

    MySeePageResponse MyPost();

}
