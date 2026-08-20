package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SimilarityResult(

        double similarityPercentage,
        List<DocumentMatchResult> matches

) {
}