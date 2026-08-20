package com.example.PLAGIARISM_SERVICE.controller;

import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import com.example.PLAGIARISM_SERVICE.feign.ResearchClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plagiarism")
public class TestController {

    private final ResearchClientService researchClientService;

    @GetMapping("/{paperId}")
    public ResearchPaperResponse test(
            @PathVariable Long paperId
    ) {
        return researchClientService.getPaper(paperId);
    }
}