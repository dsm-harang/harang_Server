package com.javaproject.harang.service.post;

import com.javaproject.harang.payload.request.PostUpdateRequest;
import com.javaproject.harang.payload.request.PostWriteRequest;
import com.javaproject.harang.payload.response.MainPageResponse;
import com.javaproject.harang.payload.response.PostListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    void postWrite(PostWriteRequest postWriteRequest);
    void postUpdate(Integer postId, PostUpdateRequest postUpdateRequest);
    void postDelete(Integer postId);
    MainPageResponse mainPage(Integer postId);
    List<PostListResponse> getPostList();
    void accept(Integer applicationId);
    void sendTest(Integer postId);
}
