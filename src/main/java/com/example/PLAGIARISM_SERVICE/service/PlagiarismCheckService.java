package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.CreatePlagiarismCheckRequest;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismCheckResponse;

public interface PlagiarismCheckService {

    PlagiarismCheckResponse createCheck(
            CreatePlagiarismCheckRequest request
    );
}