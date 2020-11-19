package com.javaproject.harang.service.user;

import com.javaproject.harang.entity.report.UserReports;
import com.javaproject.harang.entity.report.repository.UserReportRepository;
import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.exception.TargetNotFound;
import com.javaproject.harang.exception.UserAlreadyException;
import com.javaproject.harang.exception.UserAlreadyReport;
import com.javaproject.harang.exception.UserNotFound;
import com.javaproject.harang.payload.request.SignUpRequest;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value("${image.file.path}")
    private String imagePath;

    private final CustomerRepository customerRepository;
    private final UserReportRepository userReportRepository;
    private final AuthenticationFacade authenticationFacade;

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public void signUp(SignUpRequest signUpRequest) {
        customerRepository.findByUserId(signUpRequest.getUserId())
                .ifPresent(customer -> {throw new UserAlreadyException();});

        String fileName = UUID.randomUUID().toString();

        customerRepository.save(
                Customer.builder()
                        .userId(signUpRequest.getUserId())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .name(signUpRequest.getName())
                        .age(signUpRequest.getAge())
                        .phoneNumber(signUpRequest.getPhoneNumber())
                        .intro(signUpRequest.getIntro())
                        .imagePath(imagePath + "/" +fileName)
                        .build()
        );

        File file = new File(imagePath, fileName);
        signUpRequest.getImage().transferTo(file);
    }

    @Override
    public void userReport(Integer targetId, String content) {
        Integer receiptCode = authenticationFacade.getReceiptCode();
        User user = customerRepository.findById(receiptCode)
                .orElseThrow(UserNotFound::new);

        User target = customerRepository.findById(targetId)
                .orElseThrow(TargetNotFound::new);

        userReportRepository.findByUserIdAndTargetId(user.getId(), targetId)
                .ifPresent(userReport -> {throw new UserAlreadyReport();});

        userReportRepository.save(
                UserReports.builder()
                        .userId(user.getId())
                        .targetId(targetId)
                        .targetUserId(target.getUserId())
                        .targetName(target.getName())
                        .content(content)
                        .reportTime(LocalDate.now())
                        .build()
        );
    }

}
