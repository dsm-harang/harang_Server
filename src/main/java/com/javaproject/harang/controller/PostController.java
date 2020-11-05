package com.javaproject.harang.controller;

import com.javaproject.harang.payload.request.PostUpdateRequest;
import com.javaproject.harang.payload.request.PostWriteRequest;
import com.javaproject.harang.payload.response.MainPageResponse;
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

    @PostMapping()
    public void PostWrite(@RequestParam String title,
                          @RequestParam String content,
                          @RequestParam String tag,
                          @RequestParam LocalDateTime meetTime,
                          @RequestParam Integer ageLimit,
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
    public void PostDelete(@PathVariable Integer postId){
        postService.postDelete(postId);
    }

    @GetMapping("/{postId}")
    public MainPageResponse mainPage(@PathVariable Integer postId){
        return postService.mainPage(postId);
    }

    @GetMapping
    public List<PostListResponse> getPostList()   {
        return postService.getPostList();
    }

    @GetMapping("/{applicationId}")
    public void accept(@PathVariable Integer applicationId){
        postService.accept(applicationId);
    }

    @PostMapping("/{postId}")
    private void sendTest(@PathVariable Integer postId){
        postService.sendTest(postId);
    }

}
