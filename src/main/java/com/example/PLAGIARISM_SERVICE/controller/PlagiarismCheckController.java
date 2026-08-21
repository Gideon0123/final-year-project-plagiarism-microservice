package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.payload.PagedResponse;
import com.example.PLAGIARISM_SERVICE.service.PlagiarismCheckService;
import com.example.PLAGIARISM_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism/check")
public class PlagiarismCheckController {

    private final PlagiarismCheckService plagiarismCheckService;

//    @PostMapping("/checks")
    @PostMapping
    @PreAuthorize("hasAnyRole('RESEARCHER','ADMIN')")
    public ResponseEntity<ApiResponse<PlagiarismCheckResponse>> createCheck(
            @RequestBody CreatePlagiarismCheckRequest request,
            HttpServletRequest httpRequest
    ) {
        PlagiarismCheckResponse response = plagiarismCheckService.createCheck(
                request
        );

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckResponse>builder()
                        .success(true)
                        .message("Plagiarism check completed successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .path(httpRequest.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlagiarismCheckResponse>> getCheck(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        PlagiarismCheckResponse response = plagiarismCheckService.getCheck(id);

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckResponse>builder()
                        .success(true)
                        .message("Check fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/paper/{paperId}")
    public ResponseEntity<ApiResponse<PagedResponse<PlagiarismCheckResponse>>> getChecksByPaper(
            @PathVariable Long paperId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<PlagiarismCheckResponse> checks =
                plagiarismCheckService.getChecksByPaper(
                        paperId, adjustedPage, size, sortBy, sortDirection
                );

        PagedResponse<PlagiarismCheckResponse> response =
                PagedResponse.<PlagiarismCheckResponse>builder()
                        .content(checks.getContent())
                        .size(checks.getSize())
                        .page(checks.getPage())
                        .first(checks.isFirst())
                        .last(checks.isLast())
                        .totalElements(checks.getTotalElements())
                        .totalPages(checks.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<PlagiarismCheckResponse>>
                                builder()
                        .success(true)
                        .message("Checks fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/paper/{paperId}/latest")
    public ResponseEntity<ApiResponse<PlagiarismCheckResponse>> getLatestCheck(
            @PathVariable Long paperId,
            HttpServletRequest request
    ) {
        PlagiarismCheckResponse response =
                plagiarismCheckService.getLatestCheck(
                        paperId
                );

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckResponse>builder()
                        .success(true)
                        .message("Latest check fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("/{id}/rerun")
    public ResponseEntity<ApiResponse<PlagiarismCheckResponse>> rerunCheck(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        PlagiarismCheckResponse response = plagiarismCheckService.rerunCheck(id);

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckResponse>builder()
                        .success(true)
                        .message("Plagiarism check re-run successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PagedResponse<PlagiarismCheckResponse>>> getMyChecks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<PlagiarismCheckResponse> checks =
                plagiarismCheckService.getMyChecks(
                        adjustedPage, size, sortBy, sortDirection
                );

        PagedResponse<PlagiarismCheckResponse> response =
                PagedResponse.<PlagiarismCheckResponse>builder()
                        .content(checks.getContent())
                        .size(checks.getSize())
                        .page(checks.getPage())
                        .first(checks.isFirst())
                        .last(checks.isLast())
                        .totalElements(checks.getTotalElements())
                        .totalPages(checks.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<PlagiarismCheckResponse>>builder()
                        .success(true)
                        .message("Checks fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<PlagiarismCheckResponse>>> getAllChecks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            HttpServletRequest request
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        PagedResponse<PlagiarismCheckResponse> checks =
                plagiarismCheckService.getAllChecks(
                        adjustedPage, size, sortBy, sortDirection
                );

        PagedResponse<PlagiarismCheckResponse> response =
                PagedResponse.<PlagiarismCheckResponse>builder()
                        .content(checks.getContent())
                        .size(checks.getSize())
                        .page(checks.getPage())
                        .first(checks.isFirst())
                        .last(checks.isLast())
                        .totalElements(checks.getTotalElements())
                        .totalPages(checks.getTotalPages())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<PagedResponse<PlagiarismCheckResponse>>builder()
                        .success(true)
                        .message("Checks fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCheck(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        plagiarismCheckService.deleteCheck(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Check deleted successfully")
                        .status(HttpStatus.OK.value())
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<PlagiarismStatisticsResponse>> getStatistics(
            HttpServletRequest request
    ) {
        PlagiarismStatisticsResponse response = plagiarismCheckService.getStatistics();

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismStatisticsResponse>builder()
                        .success(true)
                        .message("Statistics fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PlagiarismCheckStatusResponse>> getStatus(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        PlagiarismCheckStatusResponse response = plagiarismCheckService.getCheckStatus(id);

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckStatusResponse>builder()
                        .success(true)
                        .message("Check status fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/paper/{paperId}/status")
    public ResponseEntity<
            ApiResponse<PlagiarismCheckStatusResponse>
            > getPaperStatus(
            @PathVariable Long paperId,
            HttpServletRequest request
    ) {
        PlagiarismCheckStatusResponse response = plagiarismCheckService.getPaperStatus(
                paperId
        );

        return ResponseEntity.ok(
                ApiResponse.<PlagiarismCheckStatusResponse>builder()
                        .success(true)
                        .message("Paper plagiarism status fetched successfully")
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
