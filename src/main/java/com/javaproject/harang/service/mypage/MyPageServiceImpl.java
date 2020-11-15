package com.javaproject.harang.service.mypage;

import com.javaproject.harang.entity.user.User;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.payload.request.MyPageUpdateRequest;
import com.javaproject.harang.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MypageService {

    private final CustomerRepository customerRepository;

    private final AuthenticationFacade authenticationFacade;

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

}
