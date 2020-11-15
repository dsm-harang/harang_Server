package com.javaproject.harang.service.user;

import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value("${image.file.path}")
    private String imagePath;

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public void signUp(SignUpRequest signUpRequest) {
        customerRepository.findByUserId(signUpRequest.getUserId()).ifPresent(customer -> {
            throw new RuntimeException();
        });

        String fileName = UUID.randomUUID().toString();

        customerRepository.save(
                Customer.builder()
                        .userId(signUpRequest.getUserId())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .name(signUpRequest.getName())
                        .age(signUpRequest.getAge())
                        .phoneNumber(signUpRequest.getPhoneNumber())
                        .intro(signUpRequest.getIntro())
                        .imagePath(imagePath + fileName)
                        .build()
        );

        File file = new File(imagePath, fileName);
        signUpRequest.getImage().transferTo(file);
    }

}
