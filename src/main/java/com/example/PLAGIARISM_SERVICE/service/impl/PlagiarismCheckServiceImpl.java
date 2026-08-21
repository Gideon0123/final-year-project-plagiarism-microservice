package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;
import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;
import com.example.PLAGIARISM_SERVICE.enums.CheckStatus;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.mapper.PlagiarismMapper;
import com.example.PLAGIARISM_SERVICE.payload.PagedResponse;
import com.example.PLAGIARISM_SERVICE.repository.PlagiarismCheckRepository;
import com.example.PLAGIARISM_SERVICE.repository.PlagiarismMatchRepository;
import com.example.PLAGIARISM_SERVICE.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ReportService reportService;

    private final PlagiarismCheckRepository checkRepository;
    private final PlagiarismMatchRepository matchRepository;

    private final PlagiarismMapper mapper;

    private Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }

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

        try {
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

            List<PlagiarismMatch> matches = new ArrayList<>();
            for(DocumentMatchResult matchResult : similarity.matches()) {

                for(MatchingPassage passage : matchResult.passages()) {

                    PlagiarismMatch match =
                            PlagiarismMatch.builder()
                                    .plagiarismCheck(check)
                                    .sourcePaperId(matchResult.paperId())
                                    .similarityPercentage(
                                            matchResult.similarityPercentage()
                                    )
                                    .matchingText(
                                            passage.submittedPassage()
                                    )
                                    .sourceExcerpt(
                                            passage.matchedPassage()
                                    )
                                    .build();

                    matches.add(match);
                }
            }
            matchRepository.saveAll(matches);

            check.setMatches(matches);

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

            String report = reportService.generateReport(
                    check,
                    matches
            );

            check.setReport(report);

            check = checkRepository.save(check);
            return mapper.toResponse(check);

        } catch (Exception ex){

            check.setStatus(CheckStatus.FAILED);
            check.setErrorMessage(ex.getMessage());
            check.setCompletedAt(LocalDateTime.now());
            checkRepository.save(check);

            throw ex;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public PlagiarismCheckResponse getCheck(
            Long id
    ) {
        PlagiarismCheck check = checkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Plagiarism check not found"
                        )
                );

        return mapper.toResponse(check);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PlagiarismCheckResponse> getChecksByPaper(
            Long paperId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        Page<PlagiarismCheckResponse> checks = checkRepository.findByPaperId(
                        paperId,
                        pageable
                )
                .map(mapper::toResponse);

        return new PagedResponse<>(checks);
    }

    @Override
    @Transactional(readOnly = true)
    public PlagiarismCheckResponse getLatestCheck(
            Long paperId
    ) {
        PlagiarismCheck check = checkRepository.findFirstByPaperIdOrderByCreatedAtDesc(
                paperId
        ).orElseThrow(() -> new ResourceNotFoundException(
                        "No plagiarism checks found"
                )
        );

        return mapper.toResponse(check);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<PlagiarismMatchResponse> getMatches(
            Long checkId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDirection);
        Page<PlagiarismMatchResponse> matches = matchRepository.findByPlagiarismCheckId(
                        checkId,
                        pageable
                )
                .map(mapper::toMatchResponse);

        return new PagedResponse<>(matches);
    }

    @Override
    @Transactional(readOnly = true)
    public PlagiarismMatchResponse getMatch(
            Long id
    ) {
        PlagiarismMatch match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Match not found"
                        )
                );

        return mapper.toMatchResponse(match);
    }

    @Override
    public PlagiarismCheckResponse rerunCheck(
            Long checkId
    ) {
        PlagiarismCheck existingCheck = checkRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Plagiarism check not found"
                        )
                );

        CreatePlagiarismCheckRequest request =
                CreatePlagiarismCheckRequest.builder()
                        .paperId(existingCheck.getPaperId())
                        .build();

        return createCheck(request);
    }
}