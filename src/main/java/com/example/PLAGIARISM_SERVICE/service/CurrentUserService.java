package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.entity.CurrentUser;
import com.example.PLAGIARISM_SERVICE.exceptions.AccessDeniedException;
import com.example.PLAGIARISM_SERVICE.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public CurrentUser getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null || !(authentication.getPrincipal()
                instanceof UserPrincipal principal)) {
            throw new AccessDeniedException("Not authenticated");
        }

        return CurrentUser.builder()
                .id(principal.getUserId())
                .email(principal.getEmail())
                .role(principal.getRole())
                .build();
    }

}