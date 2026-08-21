package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismMatchResponse;
import com.example.PLAGIARISM_SERVICE.payload.PagedResponse;
import com.example.PLAGIARISM_SERVICE.service.PlagiarismCheckService;
import com.example.PLAGIARISM_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism/match")
public class PlagiarismMatchController {

    private final PlagiarismCheckService plagiarismCheckService;

    @GetMapping("/{id}/matches")
    public ResponseEntity<ApiResponse<PagedResponse<PlagiarismMatchResponse>>> getMatches(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "similarityPercentage") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<PlagiarismMatchResponse> matches =
                plagiarismCheckService.getMatches(id, adjustedPage, size, sortBy, sortDirection);

        PagedResponse<PlagiarismMatchResponse> response =
                PagedResponse.<PlagiarismMatchResponse>builder()
                        .content(matches.getContent())
                        .size(matches.getSize())
                        .page(matches.getPage())
                        .first(matches.isFirst())
                        .last(matches.isLast())
                        .totalElements(matches.getTotalElements())
                        .totalPages(matches.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<PlagiarismMatchResponse>>builder()
                        .success(true)
                        .message("Matches fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlagiarismMatchResponse>> getMatch(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        PlagiarismMatchResponse response = plagiarismCheckService.getMatch(id);

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismMatchResponse>builder()
                        .success(true)
                        .message("Match fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}