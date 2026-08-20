package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record ComparisonDocument(
        Long paperId,
        String title,
        String text

) {
}