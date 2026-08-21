package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record PlagiarismStatisticsResponse(

        long totalChecks,
        long completedChecks,
        long failedChecks,
        long passedChecks,
        long flaggedChecks,
        double averageSimilarity

) {
}