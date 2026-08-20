package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record DocumentMatchResult(

        Long paperId,
        String title,
        double similarityPercentage,
        List<MatchingPassage> passages

) {
}