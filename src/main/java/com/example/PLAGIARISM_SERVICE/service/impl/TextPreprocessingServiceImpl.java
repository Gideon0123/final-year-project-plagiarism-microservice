package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.PreprocessedText;
import com.example.PLAGIARISM_SERVICE.component.DocumentStructureCleaner;
import com.example.PLAGIARISM_SERVICE.service.TextPreprocessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TextPreprocessingServiceImpl implements TextPreprocessingService {

    private final DocumentStructureCleaner documentStructureCleaner;

    @Override
    public PreprocessedText preprocess(String text) {

        if (text == null || text.isBlank()) {

            return PreprocessedText.builder()
                    .originalText("")
                    .normalizedText("")
                    .tokens(List.of())
                    .build();
        }

        String cleanedText = documentStructureCleaner.removeRepeatedLines(text);

        String normalizedText = normalize(cleanedText);

        List<String> tokens = tokenize(normalizedText);

        return PreprocessedText.builder()
                .originalText(text)
                .normalizedText(normalizedText)
                .tokens(tokens)
                .build();
    }

    private String normalize(String text) {

        return text

                // Normalize line breaks
                .replace("\r\n", "\n")
                .replace("\r", "\n")

                // Convert multiple whitespace characters to one space
                .replaceAll("\\s+", " ")

                // Remove leading/trailing whitespace
                .trim()

                // Convert to lowercase
                .toLowerCase(Locale.ROOT)

                // Normalize punctuation spacing
                .replaceAll("\\s+([,.!?;:])", "$1");
    }

    private List<String> tokenize(String text) {

        if (text.isBlank()) {
            return List.of();
        }

        return Arrays.stream(
                        text.split("\\s+")
                )
                .map(String::trim)
                .filter(token -> !token.isBlank())
                .toList();
    }
}

