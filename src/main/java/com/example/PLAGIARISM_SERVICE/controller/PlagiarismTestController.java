package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.service.ResearchFileService;
import com.example.PLAGIARISM_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism/test")
public class PlagiarismTestController {

    private final ResearchFileService researchFileService;

    @PreAuthorize("hasAnyRole('ADMIN', 'RESEARCHER', 'REVIEWER')")
    @GetMapping("/extract/{paperId}")
    public ResponseEntity<ApiResponse<ExtractedTextResponse>> extract(
            @PathVariable Long paperId,
            HttpServletRequest request
    ) {

        ExtractedTextResponse response =
                researchFileService.retrieveAndExtract(
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
}