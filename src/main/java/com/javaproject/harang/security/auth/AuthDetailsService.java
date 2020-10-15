package com.javaproject.harang.security.auth;

import com.javaproject.harang.entity.user.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserFactory userFactory;

    @Override
    public AuthDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userFactory.getAuthDetails(Integer.parseInt(id));
    }
}
