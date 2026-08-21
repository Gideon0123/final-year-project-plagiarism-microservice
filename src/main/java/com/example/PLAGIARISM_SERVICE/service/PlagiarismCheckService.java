package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.payload.PagedResponse;

public interface PlagiarismCheckService {

    PlagiarismCheckResponse createCheck(
            CreatePlagiarismCheckRequest request
    );

    PlagiarismCheckResponse getCheck(
            Long id
    );

    PagedResponse<PlagiarismCheckResponse> getChecksByPaper(
            Long paperId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    PlagiarismCheckResponse getLatestCheck(
            Long paperId
    );

    PagedResponse<PlagiarismMatchResponse> getMatches(
            Long checkId,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    PlagiarismMatchResponse getMatch(
            Long id
    );

    PlagiarismCheckResponse rerunCheck(
            Long checkId
    );

    PagedResponse<PlagiarismCheckResponse> getMyChecks(
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    PagedResponse<PlagiarismCheckResponse> getAllChecks(
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    void deleteCheck(Long id);

    PlagiarismStatisticsResponse getStatistics();

    PlagiarismCheckStatusResponse getCheckStatus(
            Long checkId
    );

    PlagiarismCheckStatusResponse getPaperStatus(
            Long paperId
    );
}