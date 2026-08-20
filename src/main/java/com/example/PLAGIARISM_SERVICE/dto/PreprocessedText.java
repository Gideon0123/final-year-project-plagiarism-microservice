package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PreprocessedText(

        String originalText,
        String normalizedText,
        List<String> tokens

) {
}