package com.example.PLAGIARISM_SERVICE.component;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JaccardSimilarityCalculator {

    public double calculate(
            List<String> firstTokens,
            List<String> secondTokens
    ) {
        if (firstTokens == null ||
                secondTokens == null ||
                firstTokens.isEmpty() ||
                secondTokens.isEmpty()) {

            return 0.0;
        }

        Set<String> first = new HashSet<>(firstTokens);

        Set<String> second = new HashSet<>(secondTokens);

        Set<String> intersection = new HashSet<>(first);

        intersection.retainAll(second);

        Set<String> union = new HashSet<>(first);

        union.addAll(second);

        if (union.isEmpty()) {
            return 0.0;
        }

        double value = ((double) intersection.size()
                / union.size()) * 100.0;
        return round(value);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
