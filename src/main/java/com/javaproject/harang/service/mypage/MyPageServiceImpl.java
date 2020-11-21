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
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.MyPostListResponse;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MypageService {

    private final CustomerRepository customerRepository;
    private final ScoreRepository scoreRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final AuthenticationFacade authenticationFacade;


    @Value("${image.file.path}")
    private String imagePath;

    @Override
    public Map<String, Object> SeeMyPage() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);


        Map<String, Object> map = new HashMap<>();

        map.put("user_id",user.getId());
        map.put("Intro", user.getIntro());
        map.put("name", user.getName());
        map.put("imagePath", user.getImagePath());

        return map;
    }

    @Override
    public Map<String, Object> SeeOtherPage(Integer Id) {
        Map<String, Object> map = new HashMap<>();

        Customer customer = customerRepository.findById(Id)
                .orElseThrow(UserNotFound::new);

        map.put("Intro", customer.getIntro());
        map.put("user_id",customer.getId());
        map.put("name", customer.getName());
        map.put("imagePath", customer.getImagePath());

        return map;
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

            customerRepository.save(user.updateFileName(imagePath + fileName));

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
        User user =customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<ScoreResponse> scoreResponses = new ArrayList<>();
        for (Score score : scoreRepository.findAllByUserId(user.getId())) {
            scoreResponses.add(
                    ScoreResponse.builder()
                            .userId(user.getId())
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
        customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        memberRepository.findByUserIdAndPostId(targetId, sendScoreRequest.getPostId())
                .orElseThrow(UserNotFound::new);

        Score.builder()
                .score((int) sendScoreRequest.getScore())
                .scoreAt(LocalDateTime.now())
                .scoreComment(sendScoreRequest.getScoreContent())
                .scoreTargetId(targetId)
                .build();

    }

    @Override
    public ListScoreResponse ListScore(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<Member> members = memberRepository.findALLByPostId(postId);
        List<Member> collect = members.stream()
                .filter(m -> !m.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        return new ListScoreResponse(collect);
    }
    

    @Override
    public List<MyPostListResponse> myPost() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<Post> postList = postRepository.findAllByUser(user);

        List<MyPostListResponse> myPostList = new ArrayList<>();
        for (Post post : postList) {
            myPostList.add(
                    MyPostListResponse.builder()
                            .postId(post.getId())
                            .postTitle(post.getTitle())
                            .build()
            );
        }

        return myPostList;
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
