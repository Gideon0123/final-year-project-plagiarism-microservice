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
        String keywords,
        Long authorId,
        String authorEmail,
        String fileName,
        String contentType,
        Long fileSize,
        String storageKey,
        ResearchStatus status,
        ResearchVisibility visibility,
        LocalDateTime createdAt
) {
}