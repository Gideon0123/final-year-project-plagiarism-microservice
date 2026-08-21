package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.PlagiarismReportResponse;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.mapper.PlagiarismMapper;
import com.example.PLAGIARISM_SERVICE.repository.PlagiarismCheckRepository;
import com.example.PLAGIARISM_SERVICE.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PlagiarismCheckRepository checkRepository;
    private final PlagiarismMapper mapper;

    @Override
    public String generateReport(
            PlagiarismCheck check,
            List<PlagiarismMatch> matches
    ) {
        StringBuilder report = new StringBuilder();

        report.append("PLAGIARISM REPORT\n");
        report.append("=========================\n\n");

        report.append("Paper ID: ").append(check.getPaperId()).append("\n");

        report.append("Similarity: ").append(check.getSimilarityPercentage())
                .append("%\n");

        report.append("Threshold: ").append(check.getThresholdPercentage())
                .append("%\n");

        report.append("Result: ").append(check.getResult()).append("\n\n");

        report.append("MATCHES\n");
        report.append("-------------------------\n");

        for(PlagiarismMatch match : matches){

            report.append("Source Paper: ").append(match.getSourcePaperId())
                    .append("\n");

            report.append("Similarity: ").append(match.getSimilarityPercentage())
                    .append("%\n");

            report.append("Submitted Text:\n").append(match.getMatchingText())
                    .append("\n\n");

            report.append("Matching Source Text:\n").append(match.getSourceExcerpt())
                    .append("\n");

            report.append("-------------------------\n");
        }

        return report.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public PlagiarismReportResponse getReport(
            Long checkId
    ) {
        PlagiarismCheck check = checkRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Plagiarism check not found"
                        )
                );

        return PlagiarismReportResponse.builder()
                .checkId(check.getId())
                .paperId(check.getPaperId())
                .similarityPercentage(check.getSimilarityPercentage())
                .thresholdPercentage(check.getThresholdPercentage())
                .result(check.getResult())
                .summary(check.getSummary())
                .startedAt(check.getStartedAt())
                .completedAt(check.getCompletedAt())
                .report(check.getReport())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadReport(
            Long checkId
    ) {
        PlagiarismCheck check = checkRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Plagiarism check not found"
                        )
                );

        String report = check.getReport();

        if(report == null || report.isBlank()) {
            throw new ResourceNotFoundException(
                    "No report available"
            );
        }

        return report.getBytes(StandardCharsets.UTF_8);
    }


}