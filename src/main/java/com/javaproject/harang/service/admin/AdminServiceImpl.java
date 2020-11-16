package com.javaproject.harang.service.admin;

import com.javaproject.harang.entity.Post.Post;
import com.javaproject.harang.entity.Post.PostRepository;
import com.javaproject.harang.entity.report.Report;
import com.javaproject.harang.entity.report.UserReports;
import com.javaproject.harang.entity.report.repository.ReportRepository;
import com.javaproject.harang.entity.report.repository.UserReportRepository;
import com.javaproject.harang.entity.score.Score;
import com.javaproject.harang.entity.score.ScoreRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.admin.Admin;
import com.javaproject.harang.entity.user.admin.AdminRepository;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.response.PostListResponse;
import com.javaproject.harang.payload.response.PostReportResponse;
import com.javaproject.harang.payload.response.UserPageResponse;
import com.javaproject.harang.payload.response.UserReportResponse;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final UserReportRepository userReportRepository;
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CustomerRepository customerRepository;
    private final ScoreRepository scoreRepository;


    private final AuthenticationFacade authenticationFacade;


    @Override
    public void userDelete(Integer userId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Customer user = customerRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        UserReports userReports = userReportRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        customerRepository.deleteById(user.getId());
        customerRepository.delete(user);

        userReportRepository.deleteById(userReports.getId());
        userReportRepository.delete(userReports);
    }

    @Override
    public void userPostDelete(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        Report report = reportRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        postRepository.deleteById(post.getId());
        postRepository.delete(post);

        reportRepository.deleteById(report.getId());
        reportRepository.delete(report);

    }

    @Override
    public void userReportDelete(Integer userId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        UserReports userReports = userReportRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        userReportRepository.deleteById(userReports.getId());
        userReportRepository.delete(userReports);

    }

    @Override
    public void postReportDelete(Integer postId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Report report = reportRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        reportRepository.deleteById(report.getId());
        reportRepository.delete(report);
    }

    @Override
    public void score(Integer userId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Score score = scoreRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        scoreRepository.deleteById(score.getId());
        scoreRepository.delete(score);
    }

    @Override
    public List<PostReportResponse> postReport() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<PostReportResponse> list = new ArrayList<>();
        for (Report report : reportRepository.findAll()) {
            Post post = postRepository.findById(report.getId())
                    .orElseThrow(RuntimeException::new);

            list.add(
                PostReportResponse.builder()
                        .id(report.getId())
                        .postId(report.getPostId())
                        .reportTime(LocalDate.now())
                        .title(post.getTitle())
                        .writer(post.getWriter())
                        .build()
            );
        }

        return list;
    }

    @Override
    public List<UserReportResponse> userReport() {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        Admin admin = adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        List<UserReportResponse> list = new ArrayList<>();
        for(UserReports reports : userReportRepository.findAll()){
            Customer customer = customerRepository.findById(reports.getId())
                    .orElseThrow(RuntimeException::new);

            list.add(
                    UserReportResponse.builder()
                        .id(reports.getId())
                        .targetId(reports.getTargetId())
                        .targetName(reports.getTargetName())
                        .reportTime(LocalDate.now())
                        .build()
            );
        }
        return list;
    }

    @Override
    public UserPageResponse userPage(Integer userId) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        adminRepository.findById(receiptCode)
                .orElseThrow(RuntimeException::new);

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        Score score = scoreRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        List<UserReports> reportList = userReportRepository.findByTargetUserId(userId);

        List<String> contents = new ArrayList<>();
        for(UserReports report :  reportList){
            contents.add(report.getContent());
        }

        File fileName = new File(customer.getImagePath());

        return UserPageResponse.builder()
                    .score(score.getScore())
                    .name(customer.getName())
                    .imageName(fileName.getName())
                    .content(contents)
                    .build();
    }
}
