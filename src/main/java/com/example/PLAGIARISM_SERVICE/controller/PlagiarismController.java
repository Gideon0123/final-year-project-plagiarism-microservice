package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.*;
import com.example.PLAGIARISM_SERVICE.service.PlagiarismCheckService;
import com.example.PLAGIARISM_SERVICE.service.ReportService;
import com.example.PLAGIARISM_SERVICE.service.ResearchFileService;
import com.example.PLAGIARISM_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism")
public class PlagiarismController {

    private final ResearchFileService researchFileService;
    private final PlagiarismCheckService plagiarismCheckService;
    private final ReportService reportService;

    @PreAuthorize("hasAnyRole('ADMIN', 'RESEARCHER', 'REVIEWER')")
    @GetMapping("/extract/{paperId}")
    public ResponseEntity<ApiResponse<ExtractedTextResponse>> extract(
            @PathVariable Long paperId,
            HttpServletRequest request
    ) {
        ExtractedTextResponse response = researchFileService.retrieveAndExtract(
                paperId
        );

        return ResponseEntity.ok(
                ApiResponse.<ExtractedTextResponse>builder()
                        .success(true)
                        .message("Research file extracted successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<ApiResponse<PlagiarismReportResponse>> getReport(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        PlagiarismReportResponse response = reportService.getReport(id);

        return ResponseEntity.ok(
                ApiResponse
                        .<PlagiarismReportResponse>builder()
                        .success(true)
                        .message("Report fetched successfully")
                        .status(HttpStatus.OK.value())
                        .data(response)
                        .errors(null)
                        .path(request.getRequestURI())
                        .traceId(TraceIdUtil.generate())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}/report/download")
    public ResponseEntity<byte[]> downloadReport(
            @PathVariable Long id
    ) {
        byte[] report = reportService.downloadReport(id);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=plagiarism-report-" +
                                id +
                                ".txt"
                )
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(report.length)
                .body(report);
    }
}