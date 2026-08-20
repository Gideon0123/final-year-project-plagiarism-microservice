package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.ResearchStatus;
import com.example.PLAGIARISM_SERVICE.enums.ResearchVisibility;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResearchPaperResponse(

        Long id,
        String title,
        String abstractText,
        Long authorId,
        String fileUrl,
        ResearchStatus status,
        ResearchVisibility visibility,
        LocalDateTime createdAt
) {
}