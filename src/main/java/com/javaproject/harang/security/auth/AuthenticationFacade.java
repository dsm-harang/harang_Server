package com.javaproject.harang.security.auth;

import com.javaproject.harang.exception.TokenNotFound;
import com.javaproject.harang.exception.UserNotFound;
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


    public Integer getReceiptCode() {
        try {
            Authentication auth = this.getAuthentication();
            if (auth.getPrincipal() instanceof AuthDetails) {
                return ((AuthDetails) auth.getPrincipal()).getUser().getId();
            } else {
                return Integer.parseInt(this.getAuthentication().getName());
            }
        } catch (NumberFormatException e) {
            throw new TokenNotFound();
        }

    }
}



