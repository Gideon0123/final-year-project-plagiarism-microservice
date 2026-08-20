package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ComparisonDocument;
import com.example.PLAGIARISM_SERVICE.enums.SimilarityResult;

import java.util.List;

public interface SimilarityService {

    SimilarityResult compare(
            Long sourcePaperId,
            String submittedText,
//            List<ResearchDocument> candidates
            List<ComparisonDocument> candidates
    );
}