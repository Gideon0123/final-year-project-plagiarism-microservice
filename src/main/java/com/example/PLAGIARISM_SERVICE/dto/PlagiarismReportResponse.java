package com.example.PLAGIARISM_SERVICE.dto;

import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;
import lombok.Builder;

@Builder
public record PlagiarismReportResponse(
        Long checkId,
//        Double similarityPercentage,
//        SimilarityResult result,
        String report
) {
}