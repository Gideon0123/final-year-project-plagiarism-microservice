package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.component.JaccardSimilarityCalculator;
import com.example.PLAGIARISM_SERVICE.component.PhraseMatchCalculator;
import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.service.SimilarityService;
import com.example.PLAGIARISM_SERVICE.service.TextPreprocessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {

    private final TextPreprocessingService textPreprocessingService;
    private final JaccardSimilarityCalculator jaccardSimilarityCalculator;
    private final PhraseMatchCalculator phraseMatchCalculator;

    @Override
    public SimilarityResult compare(
            Long sourcePaperId,
            String submittedText,
            List<ComparisonDocument> candidates
    ) {
        PreprocessedText submitted = textPreprocessingService.preprocess(
                submittedText
        );

        List<DocumentMatchResult> matches =
                candidates.stream()

                        .filter(document ->
                                !document.paperId()
                                        .equals(sourcePaperId)
                        )

                        .map(document ->
                                compareDocument(
                                        submitted,
                                        document
                                )
                        )

                        .filter(match ->
                                match.similarityPercentage() > 0
                        )

                        .sorted(
                                Comparator.comparingDouble(
                                        DocumentMatchResult::similarityPercentage
                                ).reversed()
                        )
                        .toList();

        double overallSimilarity = calculateOverallSimilarity(matches);

        return SimilarityResult.builder()
                .similarityPercentage(round(overallSimilarity))
                .matches(matches)
                .build();
    }

    private DocumentMatchResult compareDocument(
            PreprocessedText submitted,
            ComparisonDocument document
    ) {

        PreprocessedText candidate = textPreprocessingService.preprocess(
                document.text()
        );

        double similarity = jaccardSimilarityCalculator.calculate(
                submitted.tokens(),
                candidate.tokens()
        );

        List<MatchingPassage> passages = phraseMatchCalculator.findMatches(
                submitted.normalizedText(),
                candidate.normalizedText()
        );

        return DocumentMatchResult.builder()
                .paperId(document.paperId())
                .title(document.title())
                .similarityPercentage(
                        round(similarity)
                )
                .passages(passages)
                .build();
    }

    private double calculateOverallSimilarity(
            List<DocumentMatchResult> matches
    ) {
        if (matches.isEmpty()) {
            return 0.0;
        }

        return matches.stream()
                .mapToDouble(
                        DocumentMatchResult::similarityPercentage
                )
                .max()
                .orElse(0.0);
    }

    private double round(double value) {

        return Math.round(value * 100.0) / 100.0;
    }
}
