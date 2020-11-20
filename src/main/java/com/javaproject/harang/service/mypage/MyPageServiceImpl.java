package com.javaproject.harang.service.mypage;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.MemberNotFound;
import com.javaproject.harang.exception.ScoreNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.request.SendScoreRequest;
import com.javaproject.harang.payload.response.*;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MypageService {

    private final CustomerRepository customerRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ScoreRepository scoreRepository;
    private final MemberRepository memberRepository;


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
    public Map<String, Object> UpdateMyPage(MyPageUpdateRequest myPageUpdateRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        Map<String, Object> map = new HashMap<>();
        String fileName = UUID.randomUUID().toString();

        Customer customer = customerRepository.findById(user.getId())
                .orElseThrow(UserNotFound::new);

        File deleteFile = new File(user.getImagePath());
        deleteFile.delete();
        customer.setIntro(customer.getIntro());
        customer.setImagePath(imagePath + fileName);
        customerRepository.save(customer);

        File file = new File(imagePath, fileName);
        myPageUpdateRequest.getImagePath().transferTo(file);

        map.put("user_id",user.getId());
        map.put("name", user.getName());
        map.put("Intro", user.getIntro());
        map.put("imagePath", user.getImagePath());

        return map;
    }

    @Override
    public List<ScoreResponse> getScore(Integer id) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<ScoreResponse> scoreResponses = new ArrayList<>();
        for (Score score : scoreRepository.findAllByScoreTargetId(id)) {
            User user = customerRepository.findById(id)
                    .orElseThrow(UserNotFound::new);

            User target = customerRepository.findById(score.getUserId())
                    .orElseThrow(UserNotFound::new);

            scoreResponses.add(
                    ScoreResponse.builder()
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
    public List<ScoreResponse> getScore() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user =customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        List<ScoreResponse> scoreResponses = new ArrayList<>();
        for (Score score : scoreRepository.findAllByUserId(user.getId())) {
            scoreResponses.add(
                    ScoreResponse.builder()
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
    public MySeePageResponse MyPost() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        List<MyPostResponse> member = memberRepository.findALLByuserId(user.getId());
        return new MySeePageResponse(member);
    }


}
