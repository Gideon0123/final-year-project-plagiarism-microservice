package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlagiarismCheckResponse(
        Long id,
        Long paperId,
        Double similarityPercentage,
        CheckStatus status,
        SimilarityResult result,
        String summary,
        LocalDateTime createdAt
) {
}