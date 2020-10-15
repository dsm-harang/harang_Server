package com.javaproject.harang.entity.user;

import com.javaproject.harang.entity.user.admin.AdminRepository;
import com.javaproject.harang.entity.user.customer.Customer;
import com.javaproject.harang.entity.user.customer.CustomerRepository;
import com.javaproject.harang.security.AuthorityType;
import com.javaproject.harang.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public AuthDetails getAuthDetails(Integer id) {
        return customerRepository.findById(id)
                .map(customer -> new AuthDetails(customer, AuthorityType.USER))
                .orElseGet(() -> adminRepository.findById(id)
                        .map(admin -> new AuthDetails(admin, AuthorityType.ADMIN))
                        .orElseThrow(RuntimeException::new)
                );
    }

    public User getUser(String id){
        Optional<Customer> customer = customerRepository.findById(Integer.parseInt(id));
        if(customer.isPresent()) return customer.get();
        else return adminRepository.findById(Integer.parseInt(id))
            .orElseThrow(RuntimeException::new);
    }
}
