package com.javaproject.harang.service.mypage;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.MemberNotFound;
import com.javaproject.harang.exception.ScoreNotFound;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.response.*;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import com.javaproject.harang.service.notice.NotifyService;
import com.javaproject.harang.service.notice.NotifyServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.implementation.bytecode.Throw;
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

        map.put("name", user.getName());
        map.put("Intro", user.getIntro());
        map.put("imagePath", user.getImagePath());

        return map;
    }

    @Override
    public ScoreResponse GetScore(Integer Id) {
        Customer user = customerRepository.findById(Id).orElseThrow(UserNotFound::new);

        List<Score> scores = scoreRepository.findByScoreTargetId(user.getId());

        return new ScoreResponse(scores);
    }

    @Override
    public void SendScore(Integer postId, Integer score, String scoreContent, Integer scoreTargetId) {

        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);
        memberRepository.findByUserIdAndPostId(scoreTargetId,postId).orElseThrow(MemberNotFound::new);
        Member member = memberRepository.findByUserIdAndPostId(user.getId(), postId).orElseThrow(MemberNotFound::new);
        try {
            Score scores = scoreRepository.findByUserIdAndScoreTargetId(user.getId(), scoreTargetId).orElseThrow();
            if (scores.getScoreTargetId().equals(scoreTargetId)) {
                scores.setScore(score);
                scores.setScoreAt(LocalDateTime.now());
                scores.setScoreComment(scoreContent);
                scoreRepository.save(scores);
            } else {
                if (user.getId().equals(member.getUserId())) {
                    scoreRepository.save(
                            Score.builder()
                                    .score(score)
                                    .scoreAt(LocalDateTime.now())
                                    .scoreComment(scoreContent)
                                    .scoreTargetId(scoreTargetId)
                                    .userId(user.getId())
                                    .build()
                    );
                }
            }
        } catch (NoSuchElementException e) {
            if (user.getId().equals(member.getUserId())) {
                scoreRepository.save(
                        Score.builder()
                                .score(score)
                                .scoreAt(LocalDateTime.now())
                                .scoreComment(scoreContent)
                                .scoreTargetId(scoreTargetId)
                                .userId(user.getId())
                                .build()
                );
            }
        }catch (RuntimeException e) {
            throw new ScoreNotFound();
        }
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
