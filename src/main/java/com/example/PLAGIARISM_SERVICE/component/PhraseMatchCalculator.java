package com.example.PLAGIARISM_SERVICE.component;

import com.example.PLAGIARISM_SERVICE.dto.MatchingPassage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class PhraseMatchCalculator {

    private static final int DEFAULT_WINDOW_SIZE = 8;

    public List<MatchingPassage> findMatches(
            String submittedText,
            String candidateText
    ) {
        if (submittedText == null ||
                candidateText == null ||
                submittedText.isBlank() ||
                candidateText.isBlank()) {

            return List.of();
        }

        List<String> submittedTokens = tokenize(submittedText);
        List<String> candidateTokens = tokenize(candidateText);
        List<MatchingPassage> matches = new ArrayList<>();

        for (int i = 0; i <= submittedTokens.size() - DEFAULT_WINDOW_SIZE; i++) {

            List<String> window = submittedTokens.subList(
                    i, i + DEFAULT_WINDOW_SIZE
            );

            if (containsSequence(candidateTokens, window)) {

                String phrase = String.join(" ", window);

                matches.add(
                        MatchingPassage.builder()
                                .submittedPassage(phrase)
                                .matchedPassage(phrase)
                                .submittedStartPosition(i)
                                .submittedEndPosition(
                                        i + DEFAULT_WINDOW_SIZE
                                )
                                .build()
                );
            }
        }

        return matches;
    }

    private boolean containsSequence(
            List<String> candidate,
            List<String> sequence
    ) {
        if (sequence.size() > candidate.size()) {
            return false;
        }

        for (int i = 0; i <= candidate.size() - sequence.size(); i++) {

            boolean matches = true;

            for (int j = 0; j < sequence.size(); j++) {

                if (!candidate.get(i + j).equals(sequence.get(j))) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                return true;
            }
        }

        return false;
    }

    private List<String> tokenize(String text) {

        return Arrays.stream(
                        text.toLowerCase(Locale.ROOT).split("\\s+")
                )
                .filter(token ->
                        !token.isBlank()
                )
                .toList();
    }
}