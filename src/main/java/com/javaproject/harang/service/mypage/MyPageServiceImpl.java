package com.javaproject.harang.service.mypage;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.*;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.AllPostListResponse;
import com.javaproject.harang.payload.response.PageInfoResponse;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final CustomerRepository customerRepository;
    private final ScoreRepository scoreRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final AuthenticationFacade authenticationFacade;


    @Value("${image.file.path}")
    private String imagePath;

    @Override
    public PageInfoResponse getMyPage() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        File file = new File(user.getImagePath());
        return PageInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .intro(user.getIntro())
                .imagName(file.getName())
                .build();
    }

    @Override
    public PageInfoResponse getOtherPage(Integer id) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        if (user.getId().equals(id)) throw new UserAlreadyException();

        Customer customer = customerRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        File file = new File(customer.getImagePath());
        return PageInfoResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .intro(customer.getIntro())
                .imagName(file.getName())
                .build();
    }

    @SneakyThrows
    @Override
    public void updateMyPage(MyPageUpdateRequest myPageUpdateRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Customer user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        if (myPageUpdateRequest.getImagePath() != null) {

            String fileName = UUID.randomUUID().toString();

            File file = new File(imagePath, user.getImagePath());
            if (file.exists()) file.delete();

            customerRepository.save(user.updateFileName(imagePath + "/" + fileName));

            myPageUpdateRequest.getImagePath().transferTo(new File(imagePath, fileName));
        }

        setIfNotNull(user::setIntro, myPageUpdateRequest.getIntro());

        customerRepository.save(user);

    }

    @Override
    public List<ScoreResponse> getTargetScore(Integer targetId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<ScoreResponse> scoreResponses = new ArrayList<>();
        for (Score score : scoreRepository.findAllByScoreTargetId(targetId)) {
            User user = customerRepository.findById(targetId)
                    .orElseThrow(UserNotFound::new);

            User target = customerRepository.findById(score.getUserId())
                    .orElseThrow(UserNotFound::new);

            scoreResponses.add(
                    ScoreResponse.builder()
                            .userId(targetId)
                            .postId(score.getPostId())
                            .senderId(score.getUserId())
                            .score(user.getAverageScore())
                            .scoreAt(score.getScoreAt())
                            .comment(score.getScoreComment())
                            .senderName(target.getName())
                            .build()
            );
        }

        return scoreResponses;
    }

    @Override
    public List<ScoreResponse> getMyScore() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<ScoreResponse> scoreResponses = new ArrayList<>();
        for (Score score : scoreRepository.findAllByUserId(user.getId())) {
            scoreResponses.add(
                    ScoreResponse.builder()
                            .userId(user.getId())
                            .postId(score.getPostId())
                            .senderId(score.getUserId())
                            .score(user.getAverageScore())
                            .scoreAt(score.getScoreAt())
                            .comment(score.getScoreComment())
                            .senderName(user.getName())
                            .build()
            );
        }

        return scoreResponses;
    }

    @Override
    public void SendScore(Integer targetId, SendScoreRequest sendScoreRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        memberRepository.findByUserIdAndPostId(targetId, sendScoreRequest.getPostId())
                .orElseThrow(UserNotFound::new);
        try {
            Score score = scoreRepository.findByUserIdAndScoreTargetIdAndPostId(user.getId(), targetId, sendScoreRequest.getPostId()).get();
            scoreRepository.save(score.updateScore(sendScoreRequest.getScore(), LocalDateTime.now(ZoneId.of("Asia/Seoul")), sendScoreRequest.getScoreContent()));
        } catch (NoSuchElementException e) {
            scoreRepository.save(
                    Score.builder()
                            .userId(user.getId())
                            .postId(sendScoreRequest.getPostId())
                            .score((int) sendScoreRequest.getScore())
                            .scoreAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                            .scoreComment(sendScoreRequest.getScoreContent())
                            .scoreTargetId(targetId)
                            .build()
            );
        } catch (RuntimeException e) {
            throw new AllReadyScoreException();
        }

    }

    @Override
    public List<ListScoreResponse> listScore(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        List<Member> memberScoreList = memberRepository.findALLByPostId(postId);

        List<ListScoreResponse> scoreResponseList = new ArrayList<>();
        for (Member member : memberScoreList) {
            File file = new File(member.getImagePath());
            scoreResponseList.add(
                    ListScoreResponse.builder()
                            .postId(member.getPostId())
                            .userUuid(member.getUserId())
                            .userName(member.getUserName())
                            .imageName(file.getName())
                            .build()
            );
        }
        List<ListScoreResponse> result = scoreResponseList.stream()
                .filter(m -> !m.getUserUuid().equals(user.getId()))
                .collect(Collectors.toList());

        return result;
    }


    @Override
    public List<AllPostListResponse> myPost() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<Post> postList = postRepository.findAllByUser(user);

        List<AllPostListResponse> myPostList = new ArrayList<>();
        for (Post post : postList) {
            myPostList.add(
                    AllPostListResponse.builder()
                            .postId(post.getId())
                            .postTitle(post.getTitle())
                            .build()
            );
        }

        return myPostList;
    }

    @Override
    public List<AllPostListResponse> targetPost(Integer targetId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        Customer target = customerRepository.findById(targetId)
                .orElseThrow(TargetNotFound::new);

        List<Post> postList = postRepository.findAllByUser(target);

        List<AllPostListResponse> targetPostList = new ArrayList<>();
        for (Post post : postList) {
            targetPostList.add(
                    AllPostListResponse.builder()
                            .postId(post.getId())
                            .postTitle(post.getTitle())
                            .build()
            );
        }

        return targetPostList;
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
