package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResearchTextIndexResponse(

        Long id,
        Long paperId,
        String title,
        Integer tokenCount,
        LocalDateTime indexedAt

) {
}
