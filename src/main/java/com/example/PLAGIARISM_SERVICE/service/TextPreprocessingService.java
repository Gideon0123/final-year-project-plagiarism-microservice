package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.PreprocessedText;

public interface TextPreprocessingService {

    PreprocessedText preprocess(String text);
}