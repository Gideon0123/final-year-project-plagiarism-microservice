package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record ExtractedTextResponse(

        String text,
        Integer wordCount,
        Integer characterCount
) {
}
