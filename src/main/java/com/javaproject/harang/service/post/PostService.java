package com.javaproject.harang.service.post;

import com.javaproject.harang.payload.request.PostUpdateRequest;
import com.javaproject.harang.payload.request.PostWriteRequest;
import com.javaproject.harang.payload.response.GetPostResponse;
import com.javaproject.harang.payload.response.PostListResponse;

import java.util.List;

public interface    PostService {
    void postWrite(PostWriteRequest postWriteRequest);
    void postUpdate(Integer postId, PostUpdateRequest postUpdateRequest);
    void postDelete(Integer postId);
    GetPostResponse getPost(Integer postId);
    List<PostListResponse> getPostList();
    void accept(Integer applicationId);
    void sendPost(Integer postId);
}
