package com.example.PLAGIARISM_SERVICE.feign;

import com.example.PLAGIARISM_SERVICE.config.FeignConfig;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "RESEARCH-SERVICE",
        configuration = FeignConfig.class
)
public interface ResearchClientService {

    ResearchPaperResponse getPaper(Long paperId);
}