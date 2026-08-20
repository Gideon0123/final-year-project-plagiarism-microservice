package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record ResearchFileResponse(
        Long paperId,
        String fileName,
        String contentType,
        Long fileSize,
        String downloadUrl

) {
}