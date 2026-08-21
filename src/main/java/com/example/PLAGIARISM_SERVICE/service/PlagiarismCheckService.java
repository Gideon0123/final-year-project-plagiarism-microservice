package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.CreatePlagiarismCheckRequest;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismCheckResponse;

import java.util.List;

public interface PlagiarismCheckService {

    PlagiarismCheckResponse createCheck(
            CreatePlagiarismCheckRequest request
    );

    PlagiarismCheckResponse getCheck(
            Long id
    );

    List<PlagiarismCheckResponse> getChecksByPaper(
            Long paperId
    );

    PlagiarismCheckResponse getLatestCheck(
            Long paperId
    );
}