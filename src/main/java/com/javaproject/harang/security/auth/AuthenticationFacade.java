package com.javaproject.harang.security.auth;

import com.javaproject.harang.security.AuthorityType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getid() {
        return this.getAuthentication().getName();
    }

    public AuthorityType getAuthorityType() {
        return ((AuthDetails) this.getAuthentication().getPrincipal()).getAuthorityType();
    }

}
