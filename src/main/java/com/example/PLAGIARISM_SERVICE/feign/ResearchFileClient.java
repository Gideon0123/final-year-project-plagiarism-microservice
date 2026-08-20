package com.example.PLAGIARISM_SERVICE.feign;

import com.example.PLAGIARISM_SERVICE.config.FeignConfig;
import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchFileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "RESEARCH-SERVICE",
        contextId = "researchFileClient",
        configuration = FeignConfig.class
)
public interface ResearchFileClient {

    @GetMapping("/research/papers/{paperId}/file")
    ResponseEntity<byte[]> downloadResearchFile(
            @PathVariable("paperId") Long paperId
    );

    @GetMapping("/research/papers/{paperId}/file/metadata")
    ApiResponse<ResearchFileResponse> getResearchFileMetadata(
            @PathVariable("paperId") Long paperId
    );
}