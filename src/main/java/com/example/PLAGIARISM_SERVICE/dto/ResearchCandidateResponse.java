package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.ResearchStatus;
import com.example.PLAGIARISM_SERVICE.enums.ResearchVisibility;
import lombok.Builder;

@Builder
public record ResearchCandidateResponse(

        Long id,
        String title,
        Long authorId,
        String storageKey,
        String fileName,
        ResearchStatus status,
        ResearchVisibility visibility
) {
}