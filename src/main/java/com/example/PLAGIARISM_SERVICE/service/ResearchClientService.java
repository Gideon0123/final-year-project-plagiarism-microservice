package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;

public interface ResearchClientService {

    ResearchPaperResponse getPaper(
            Long paperId
    );
}