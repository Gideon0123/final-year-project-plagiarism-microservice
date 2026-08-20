package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import com.example.PLAGIARISM_SERVICE.feign.ResearchClientService;
import com.example.PLAGIARISM_SERVICE.utils.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism/test")
public class TestController {

    private final ResearchClientService researchClientService;

    @GetMapping("/{paperId}")
    public ResponseEntity<ApiResponse<ResearchPaperResponse>> test(
            @PathVariable Long paperId,
            HttpServletRequest request
    ) {
        ResearchPaperResponse response = researchClientService.getPaper(
                paperId
        );

        return ResponseEntity.ok(
                ApiResponse.<ResearchPaperResponse>builder()
                        .success(true)
                        .message("Research paper fetched successfully")
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