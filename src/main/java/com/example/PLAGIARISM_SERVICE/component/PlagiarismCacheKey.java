package com.example.PLAGIARISM_SERVICE.component;

import com.example.PLAGIARISM_SERVICE.service.CurrentUserService;
import com.example.PLAGIARISM_SERVICE.utils.CacheKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("plagiarismCacheKey")
@RequiredArgsConstructor
public class PlagiarismCacheKey {

    private final CurrentUserService currentUserService;

    public String myChecks(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        return CacheKeys.myChecks(
                currentUserService.getCurrentUser().getId(),
                page,
                size,
                sortBy,
                sortDirection
        );
    }
}