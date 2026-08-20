package com.example.PLAGIARISM_SERVICE.dto;

import lombok.Builder;

@Builder
public record MatchingPassage(

        String submittedPassage,
        String matchedPassage,
        int submittedStartPosition,
        int submittedEndPosition

) {
}