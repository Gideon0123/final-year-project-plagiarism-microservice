package com.example.PLAGIARISM_SERVICE.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DocumentStructureCleaner {

    public String removeRepeatedLines(String text) {

        if (text == null || text.isBlank()) {
            return "";
        }

        String[] lines = text.split("\\R");

        Map<String, Integer> frequencies =
                Arrays.stream(lines)
                        .map(String::trim)
                        .filter(line -> !line.isBlank())
                        .collect(
                                Collectors.groupingBy(
                                        String::toLowerCase,
                                        Collectors.summingInt(value -> 1)
                                )
                        );

        int totalLines = lines.length;

        Set<String> repeatedLines =
                frequencies.entrySet()
                        .stream()
                        .filter(entry ->
                                entry.getValue() >= 3 &&
                                        entry.getValue() >= Math.max(
                                                3,
                                                totalLines / 4
                                        )
                        )
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());

        return Arrays.stream(lines)
                .filter(line ->
                        !repeatedLines.contains(
                                line.trim().toLowerCase()
                        )
                )
                .collect(Collectors.joining("\n"));
    }
}
