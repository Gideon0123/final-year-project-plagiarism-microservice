package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.feign.ResearchClientService;
import com.example.PLAGIARISM_SERVICE.feign.ResearchServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResearchClientServiceImpl implements ResearchClientService {

    private final ResearchServiceClient researchServiceClient;

    @Override
    public ResearchPaperResponse getPaper(
            Long paperId
    ) {
        ApiResponse<ResearchPaperResponse> response = researchServiceClient.getPaperById(
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