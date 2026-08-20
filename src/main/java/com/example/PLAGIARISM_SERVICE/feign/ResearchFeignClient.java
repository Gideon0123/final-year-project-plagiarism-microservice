package com.example.PLAGIARISM_SERVICE.feign;

import com.example.PLAGIARISM_SERVICE.config.FeignConfig;
import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchCandidateResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "RESEARCH-SERVICE",
        contextId = "researchFeignClient",
        configuration = FeignConfig.class
)
public interface ResearchFeignClient {

    @GetMapping("/research/papers/{paperId}")
    ApiResponse<ResearchPaperResponse> getPaperById(
            @PathVariable("paperId") Long paperId
    );

    @GetMapping("/research/papers/published")
    ApiResponse<List<ResearchCandidateResponse>> getPublishedResearches();

}