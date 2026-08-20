package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlagiarismCheckResponse(
        Long id,
        Long paperId,
        Double similarityPercentage,
        Boolean passed,
        String status,
        Integer totalMatches,
        LocalDateTime createdAt

) {
}