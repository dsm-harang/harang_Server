package com.javaproject.harang.service.mypage;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MyPostForm;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.notify.Notify;
import com.javaproject.harang.entity.notify.NotifyRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.payload.response.ListScoreResponse;
import com.javaproject.harang.payload.response.NotifyResponse;
import com.javaproject.harang.payload.response.MySeePageResponse;
import com.javaproject.harang.payload.response.ScoreResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MypageService {

    private final CustomerRepository customerRepository;

    private final AuthenticationFacade authenticationFacade;
    private final ScoreRepository scoreRepository;
    private final MemberRepository memberRepository;
    private final EntityManager entityManager;
    private final NotifyRepository notifyRepository;
    @Value("${image.file.path}")
    private String imagePath;

    @Override
    public Map<String, Object> SeeMyPage() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

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
                .orElseThrow(RuntimeException::new);

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
                .orElseThrow(RuntimeException::new);

        Map<String, Object> map = new HashMap<>();
        String fileName = UUID.randomUUID().toString();

        Customer customer = customerRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);

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
        Customer user = customerRepository.findById(Id).orElseThrow(RuntimeException::new);
        List<Score> scores = scoreRepository.findAllByUserId(user.getId());


        return new ScoreResponse(scores);
    }

    @Override
    public Map<String, Object> SendScore(Integer postId, Integer score, String score_content, Integer score_target_id) {
        Map<String, Object> map = new HashMap<>();

        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);
        Member member = memberRepository.findByPostId(postId).orElseThrow();

        if (user.getId().equals(member.getUserId())) {
            scoreRepository.save(
                    Score.builder()
                            .score(score)
                            .score_at(LocalDateTime.now())
                            .score_comment(score_content)
                            .score_target_id(score_target_id)
                            .userId(user.getId())
                            .build()
            );
            map.put("message", "success");
        }

        return map;
    }

    @Override
    public ListScoreResponse ListScore() {
        List<com.javaproject.harang.controller.ScoreListResponse> list = new ArrayList<>();
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

//        Member member = memberRepository.findByUserId(user.getId()).orElseThrow();
        List<MyPostForm> postForms =memberRepository.findALLByuserId(user.getId());
        List<Member> members = new ArrayList<>();
        postForms.stream()
                .forEach(p -> {
                    members.addAll(memberRepository.findALLByPostId(p.getPostId()));
                });
//        List<Member> memberList = memberRepository.findByAllUserId(member);

        // return members;
        return new ListScoreResponse(members);
    }

    @Override
    public MySeePageResponse MyPost() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);
        List<MyPostForm> member =memberRepository.findALLByuserId(user.getId());

        System.out.println(member);
        return new MySeePageResponse(member);
    }

    @Override
    public NotifyResponse MyNotify() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);
        List<Notify> notify = notifyRepository.findAllByUserId(user.getId());
        return new NotifyResponse(notify);
    }
}
