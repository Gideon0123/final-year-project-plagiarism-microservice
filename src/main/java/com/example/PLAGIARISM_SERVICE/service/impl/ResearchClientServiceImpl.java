package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.feign.ResearchFeignClient;
import com.example.PLAGIARISM_SERVICE.service.ResearchClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearchClientServiceImpl implements ResearchClientService {

    private final ResearchFeignClient researchFeignClient;

    @Override
    public ResearchPaperResponse getPaper(
            Long paperId
    ) {
        ApiResponse<ResearchPaperResponse> response = researchFeignClient.getPaperById(
                paperId
        );

        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new ResourceNotFoundException(
                    "Paper not found"
            );
        }

        return response.getData();
    }
}