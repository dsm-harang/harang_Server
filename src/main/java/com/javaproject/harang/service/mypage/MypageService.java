package com.javaproject.harang.service.mypage;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.MyPostListResponse;
import com.javaproject.harang.payload.response.ScoreResponse;

import java.util.List;
import java.util.Map;

public interface MypageService {
    Map<String, Object> SeeMyPage();
    Map<String, Object> SeeOtherPage(Integer Id);
    void updateMyPage(MyPageUpdateRequest myPageUpdateRequest);
    List<ScoreResponse> getScore(Integer id);
    List<ScoreResponse> getScore();
    void SendScore(Integer targetId, SendScoreRequest sendScoreRequest);
    ListScoreResponse ListScore(Integer postId);
    List<MyPostListResponse> myPost();
}
