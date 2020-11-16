package com.javaproject.harang.service.post;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.application.Application;
import com.javaproject.harang.entity.application.ApplicationRepository;
import com.javaproject.harang.entity.application.eunm.Status;
import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.member.MemberRepository;
import com.javaproject.harang.entity.report.Report;
import com.javaproject.harang.entity.report.repository.ReportRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.request.PostUpdateRequest;
import com.javaproject.harang.payload.request.PostWriteRequest;
import com.javaproject.harang.payload.response.GetPostResponse;
import com.javaproject.harang.payload.response.PostListResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Value("${image.file.path}")
    private String imagePath;

    private final CustomerRepository customerRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final ScoreRepository scoreRepository;

    private final AuthenticationFacade authenticationFacade;

    @SneakyThrows
    @Override
    public void postWrite(PostWriteRequest postWriteRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        String fileName = UUID.randomUUID().toString();

        Post post = postRepository.save(
                Post.builder()
                        .userId(user.getId())
                        .title(postWriteRequest.getTitle())
                        .content(postWriteRequest.getContent())
                        .createdAt(LocalDateTime.now())
                        .tag(postWriteRequest.getTag())
                        .meetTime(postWriteRequest.getMeetTime())
                        .address(postWriteRequest.getAddress())
                        .ageLimit(postWriteRequest.getAgeLimit())
                        .personnel(postWriteRequest.getPersonnel())
                        .image(postWriteRequest.getImage() + fileName)
                        .writer(user.getName())
                        .build()
        );

        memberRepository.findByUserIdAndPostId(user.getId(), post.getId())
                .ifPresent(member -> {
                    throw new RuntimeException();
                });

        memberRepository.save(
                Member.builder()
                        .postId(post.getId())
                        .userId(user.getId())
                        .build()
        );

        File file = new File(imagePath, fileName);
        postWriteRequest.getImage().transferTo(file);
    }

    @SneakyThrows
    @Override
    public void postUpdate(Integer postId, PostUpdateRequest postUpdateRequest) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        if (!user.getId().equals(post.getUserId())) throw new RuntimeException();

        if (postUpdateRequest.getImage() != null) {

            String fileName = UUID.randomUUID().toString();

            File file = new File(imagePath, post.getImage());
            if (file.exists()) file.delete();

            postRepository.save(post.updateFileName(imagePath + fileName));

            postUpdateRequest.getImage().transferTo(new File(imagePath, fileName));
        }

        LocalDateTime meetTime = postUpdateRequest.getMeetTime();
        if (meetTime != null) post.updateMeetTime(meetTime);

        setIfNotNull(post::setTitle, postUpdateRequest.getTitle());
        setIfNotNull(post::setContent, postUpdateRequest.getContent());
        setIfNotNull(post::setTag, postUpdateRequest.getTag());
        setIfNotNull(post::setAddress, postUpdateRequest.getAddress());
        setIfNotNull(post::setAgeLimit, postUpdateRequest.getAgeLimit());
        setIfNotNull(post::setPersonnel, postUpdateRequest.getPersonnel());

        postRepository.save(post);
    }

    @Override
    @Transactional
    public void postDelete(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        if (!user.getId().equals(post.getUserId())) throw new RuntimeException();

        Post postImage = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        File fileName = new File(post.getImage());
        fileName.getName();

        File file = new File(imagePath, String.valueOf(fileName));
        if (file.exists()) file.delete();

        postRepository.deleteById(post.getId());
        postRepository.delete(post);
        postRepository.delete(postImage);
    }

    @Override
    public GetPostResponse getPost(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        boolean isMine;

        if (user.getUserId().equals(post.getUserId())) {
            isMine = true;
        } else
            isMine = false;

        return GetPostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .ageLimit(post.getAgeLimit())
                .createdAt(post.getCreatedAt())
                .meetTime(post.getMeetTime())
                .personnel(post.getPersonnel())
                .address(post.getAddress())
                .isMine(isMine)
                .build();
    }

    @Override
    public List<PostListResponse> getPostList() {
        List<PostListResponse> list = new ArrayList<>();

        List<Post> posts = (List<Post>) postRepository.findAll();

        for (Post post : posts) {

            Score score = scoreRepository.findByUserId(post.getUserId())
                    .orElseThrow(RuntimeException::new);

            File fileName = new File(post.getImage());
            list.add(
                    PostListResponse.builder()
                            .postId(post.getId())
                            .score(score.getScore())
                            .userId(post.getUserId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .writer(post.getWriter())
                            .meetTime(post.getMeetTime())
                            .address(post.getAddress())
                            .ageLimit(post.getAgeLimit())
                            .createdAt(post.getCreatedAt())
                            .personnel(post.getPersonnel())
                            .imageName(fileName.getName())
                            .build()
            );
        }
        return list;
    }

    @Override
    public void accept(Integer applicationId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(RuntimeException::new);

        Post posts = postRepository.findById(application.getPostId())
                .filter(post -> user.getId().equals(post.getUserId()))
                .orElseThrow(RuntimeException::new);

        application.accept();

        memberRepository.save(
                Member.builder()
                        .postId(posts.getId())
                        .userId(application.getUserId())
                        .build()
        );
    }

    @Override
    public void sendPost(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        if (user.getUserId().equals(post.getUserId())) throw new RuntimeException();

        Integer memberCount = memberRepository.countAllByPostId(postId);
        if (memberCount >= post.getPersonnel()) throw new RuntimeException();

        applicationRepository.findByUserIdAndPostIdAndAndStatus(user.getId(), postId, Status.READY)
                .ifPresent(application -> {
                    throw new RuntimeException();
                });

        memberRepository.findByUserIdAndPostId(user.getId(), postId)
                .ifPresent(member -> {
                    throw new RuntimeException();
                });

        applicationRepository.save(
                Application.builder()
                        .postId(postId)
                        .userId(user.getId())
                        .targetId(post.getUserId())
                        .status(Status.READY)
                        .appliedAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public void report(Integer postId, String content) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        reportRepository.findByUserIdAndPostId(user.getId(), postId)
                .ifPresent(report -> {throw new RuntimeException();});

        reportRepository.save(
                Report.builder()
                        .userId(user.getId())
                        .postId(postId)
                        .reportTime(LocalDate.now())
                        .build()
        );
    }

    @Override
    public List<PostListResponse> searchTag(String tag) {
        List<Post> posts = postRepository.findByTagContainsOrTitleContains(tag, tag);

        List<PostListResponse> list = new ArrayList<>();
        for (Post post : posts) {
            File fileName = new File(post.getImage());
            list.add(
                    PostListResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .writer(post.getWriter())
                        .meetTime(post.getMeetTime())
                        .address(post.getAddress())
                        .ageLimit(post.getAgeLimit())
                        .createdAt(post.getCreatedAt())
                        .personnel(post.getPersonnel())
                        .imageName(fileName.getName())
                        .build()
            );
        }
        return list;
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
