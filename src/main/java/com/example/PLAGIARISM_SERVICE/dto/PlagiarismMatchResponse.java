package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record PlagiarismMatchResponse(
        Long id,
        Long sourcePaperId,
        Double similarityPercentage,
        String matchingText,
        String sourceExcerpt,
        Integer sourcePage,
        Integer targetPage
) {
}