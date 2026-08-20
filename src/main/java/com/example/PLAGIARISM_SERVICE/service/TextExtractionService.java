package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;

public interface TextExtractionService {

    ExtractedTextResponse extractText(
            byte[] fileContent,
            String fileName
    );
}