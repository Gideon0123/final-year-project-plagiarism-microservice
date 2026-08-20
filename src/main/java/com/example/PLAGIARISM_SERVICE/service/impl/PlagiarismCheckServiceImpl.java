package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;
import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import com.example.PLAGIARISM_SERVICE.mapper.PlagiarismMapper;
import com.example.PLAGIARISM_SERVICE.repository.PlagiarismCheckRepository;
import com.example.PLAGIARISM_SERVICE.repository.PlagiarismMatchRepository;
import com.example.PLAGIARISM_SERVICE.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlagiarismCheckServiceImpl implements PlagiarismCheckService {

    private static final double DEFAULT_THRESHOLD = 20.0;

    private final ResearchClientService researchClientService;
    private final ResearchFileService researchFileService;
    private final CandidateRetrievalService candidateRetrievalService;
    private final ResearchTextIndexService researchTextIndexService;
    private final SimilarityService similarityService;

    private final PlagiarismCheckRepository checkRepository;
    private final PlagiarismMatchRepository matchRepository;

    private final PlagiarismMapper mapper;

    @Override
    public PlagiarismCheckResponse createCheck(
            CreatePlagiarismCheckRequest request
    ) {

        // implementation here
        ResearchPaperResponse paper = researchClientService.getPaper(
                request.paperId()
        );

        PlagiarismCheck check =
                PlagiarismCheck.builder()
                        .paperId(paper.id())
                        .authorId(paper.authorId())
                        .status(CheckStatus.PROCESSING)
                        .thresholdPercentage(DEFAULT_THRESHOLD)
                        .startedAt(LocalDateTime.now())
                        .build();

        check = checkRepository.save(check);

        ExtractedTextResponse extracted = researchFileService.retrieveAndExtract(
                paper.id()
        );

        List<ResearchCandidateResponse> candidates =
                candidateRetrievalService.retrieveCandidates();

        List<ComparisonDocument> comparisonDocuments =
                candidates.stream()

                        .filter(candidate ->
                                !candidate.id().equals(
                                        paper.id()
                                )
                        )
                        .map(candidate -> {
                            ResearchTextIndex index = researchTextIndexService
                                    .getByPaperId(candidate.id());

                            return ComparisonDocument.builder()
                                    .paperId(candidate.id())
                                    .title(candidate.title())
                                    .text(index.getNormalizedText())
                                    .build();
                        })
                        .toList();

        SimilarityResult similarity = similarityService.compare(
                paper.id(),
                extracted.text(),
                comparisonDocuments
        );

        for(DocumentMatchResult matchResult : similarity.matches()) {

            for(MatchingPassage passage : matchResult.passages()) {

                PlagiarismMatch match =
                        PlagiarismMatch.builder()
                                .plagiarismCheck(check)
                                .sourcePaperId(matchResult.paperId())
                                .similarityPercentage(
                                        matchResult.similarityPercentage()
                                )
                                .matchingText(passage.submittedPassage())
                                .sourceExcerpt(passage.sourcePassage())
                                .build();

                matchRepository.save(match);
            }
        }
        boolean passed = similarity.similarityPercentage() < DEFAULT_THRESHOLD;

        check.setSimilarityPercentage(similarity.similarityPercentage());
        check.setCompletedAt(LocalDateTime.now());
        check.setStatus(CheckStatus.COMPLETED);

        check.setResult(passed ? com.example.PLAGIARISM_SERVICE.enums.SimilarityResult.PASSED
                : com.example.PLAGIARISM_SERVICE.enums.SimilarityResult.FAILED
        );

        check.setSummary(passed ? "Similarity below threshold"
                : "Similarity exceeds threshold"
        );

        check = checkRepository.save(check);
        return mapper.toResponse(check);

    }
}