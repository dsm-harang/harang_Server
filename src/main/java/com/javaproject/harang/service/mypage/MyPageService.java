package com.javaproject.harang.service.mypage;

import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.AllPostListResponse;
import com.javaproject.harang.payload.response.PageInfoResponse;
import com.javaproject.harang.payload.response.ScoreResponse;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    PageInfoResponse getMyPage();
    PageInfoResponse getOtherPage(Integer id);
    void updateMyPage(MyPageUpdateRequest myPageUpdateRequest);
    List<ScoreResponse> getTargetScore(Integer targetId);
    List<ScoreResponse> getMyScore();
    void SendScore(Integer targetId, SendScoreRequest sendScoreRequest);
    List<ListScoreResponse> listScore(Integer postId);
    List<AllPostListResponse> myPost();
    List<AllPostListResponse> targetPost(Integer targetId);
}
