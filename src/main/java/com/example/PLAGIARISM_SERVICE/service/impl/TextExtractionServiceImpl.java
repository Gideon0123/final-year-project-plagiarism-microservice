package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.service.DocxExtractionService;
import com.example.PLAGIARISM_SERVICE.service.PdfExtractionService;
import com.example.PLAGIARISM_SERVICE.service.TextExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextExtractionServiceImpl
        implements TextExtractionService {

    private final PdfExtractionService pdfExtractionService;
    private final DocxExtractionService docxExtractionService;

    @Override
    public ExtractedTextResponse extractText(
            byte[] fileContent,
            String fileName
    ) {
        String extractedText;

        if (fileName.toLowerCase().endsWith(".pdf")
        ) {
            extractedText = pdfExtractionService.extractText(
                    fileContent
            );

        } else if (fileName.toLowerCase().endsWith(".docx")) {
            extractedText = docxExtractionService.extractText(
                    fileContent
            );

        } else {
            throw new IllegalArgumentException(
                    "Unsupported file format"
            );
        }

        return ExtractedTextResponse.builder()
                .text(extractedText)
                .wordCount(
                        extractedText.split("\\s+").length
                )
                .characterCount(extractedText.length())
                .build();
    }
}