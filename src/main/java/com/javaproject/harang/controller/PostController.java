package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.PostUpdateRequest;
import com.javaproject.harang.payload.request.PostWriteRequest;
import com.javaproject.harang.payload.response.AcceptListResponse;
import com.javaproject.harang.payload.response.GetPostResponse;
import com.javaproject.harang.payload.response.PostListResponse;
import com.javaproject.harang.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public GetPostResponse getPost(@PathVariable Integer postId){
        return postService.getPost(postId);
    }

    @GetMapping
    public List<PostListResponse> getPostList()   {
        return postService.getPostList();
    }

    @GetMapping("/accept/{applicationId}")
    public void accept(@PathVariable Integer applicationId){
        postService.accept(applicationId);
    }

    @GetMapping("/tag")
    public List<PostListResponse> searchTag(@RequestParam String tag) {
        return postService.searchTag(tag);
    }

    @GetMapping("/accept/list/{postId}")
    public List<AcceptListResponse> acceptPostList(@PathVariable Integer postId){
        return postService.acceptPostList(postId);
    }

    @PostMapping()
    public void PostWrite(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String tag,
                          @RequestParam Integer ageLimit,
                          @RequestParam LocalDateTime meetTime,
                          @RequestParam String address,
                          @RequestParam Integer personnel,
                          @RequestParam MultipartFile image) {

        postService.postWrite(
                PostWriteRequest.builder()
                    .title(title)
                    .content(content)
                    .tag(tag)
                    .meetTime(meetTime)
                    .ageLimit(ageLimit)
                    .address(address)
                    .personnel(personnel)
                    .image(image)
                    .build()
        );
    }

    @PostMapping("/{postId}")
        private void sendPost(@PathVariable Integer postId){
        postService.sendPost(postId);
    }

    @PostMapping("/report/{postId}")
    private void report(@PathVariable Integer postId){
        postService.report(postId);
    }

    @PutMapping("/{postId}")
    public void postUpdate(@PathVariable Integer postId,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam String tag,
                           @RequestParam LocalDateTime meetTime,
                           @RequestParam Integer ageLimit,
                           @RequestParam String address,
                           @RequestParam Integer personnel,
                           @RequestParam MultipartFile image){

        postService.postUpdate(postId,
                PostUpdateRequest.builder()
                        .title(title)
                        .content(content)
                        .tag(tag)
                        .meetTime(meetTime)
                        .ageLimit(ageLimit)
                        .address(address)
                        .personnel(personnel)
                        .image(image)
                        .build()
        );
    }

    @DeleteMapping("/{postId}")
    public void postDelete(@PathVariable Integer postId){
        postService.postDelete(postId);
    }

}
