package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record CreatePlagiarismCheckRequest(
        Long paperId
) {
}