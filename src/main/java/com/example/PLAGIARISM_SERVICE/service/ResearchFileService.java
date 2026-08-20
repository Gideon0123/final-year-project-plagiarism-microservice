package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;

public interface ResearchFileService {

    ExtractedTextResponse retrieveAndExtract(
            Long paperId
    );
}