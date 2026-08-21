package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlagiarismCheckStatusResponse(

        Long checkId,
        Long paperId,
        CheckStatus status,
        Double similarityPercentage,
        SimilarityResult result,
        LocalDateTime startedAt,
        LocalDateTime completedAt

) {
}