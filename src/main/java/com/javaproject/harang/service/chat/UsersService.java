package com.javaproject.harang.service.chat;

import com.javaproject.harang.entity.user.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
//
//
//    @Transactional(readOnly = true)
//    public Customer findByName(String str) {
//        return customerRepository.findUsersByName(str);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer user = customerRepository.findUsersByEmail(username);
//        return new User(user.getEmail(),user.getPassword(),authorities());
//    }
//
//    private Collection<? extends GrantedAuthority> authorities() {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//    @Transactional(readOnly = true)
//    public Customer findByEmail(String email) {
//        return customerRepository.findUsersByEmail(email);
//    }
//    @Transactional(readOnly = true)
//    public List<String> findNameByContaining(String receiver) {
//        return customerRepository.findNameByContaining(receiver);
//    }
//    @Transactional(readOnly = true)
//    public Optional<Customer> findById(Integer User_id) {
//        return customerRepository.findById(User_id);
//    }
}
