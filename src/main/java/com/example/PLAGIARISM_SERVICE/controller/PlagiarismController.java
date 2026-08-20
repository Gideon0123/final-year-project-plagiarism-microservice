package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.CreatePlagiarismCheckRequest;
import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.dto.PlagiarismCheckResponse;
import com.example.PLAGIARISM_SERVICE.service.PlagiarismCheckService;
import com.example.PLAGIARISM_SERVICE.service.ResearchFileService;
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
@RequestMapping("/plagiarism/test")
public class PlagiarismController {

    private final ResearchFileService researchFileService;
    private final PlagiarismCheckService plagiarismCheckService;

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

    @PostMapping("/checks")
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
}