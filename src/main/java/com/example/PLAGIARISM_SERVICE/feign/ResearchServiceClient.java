package com.example.PLAGIARISM_SERVICE.feign;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "RESEARCH-SERVICE"
)
public interface ResearchServiceClient {

    @GetMapping("/research/papers/{id}")
    ApiResponse<ResearchPaperResponse> getPaperById(
            @PathVariable Long id
    );
}

