package com.example.PLAGIARISM_SERVICE.feign;

import com.example.PLAGIARISM_SERVICE.config.FeignConfig;
import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchFileResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(
            value = "/research/papers/{paperId}/file"
    )
    ResponseEntity<byte[]> downloadResearchFile(
            @PathVariable Long paperId
    );
}