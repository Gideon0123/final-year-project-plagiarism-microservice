package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PlagiarismReportResponse(

        Long checkId,
        Long paperId,
        Double similarityPercentage,
        Double thresholdPercentage,
        SimilarityResult result,
        String summary,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        String report

) {
}