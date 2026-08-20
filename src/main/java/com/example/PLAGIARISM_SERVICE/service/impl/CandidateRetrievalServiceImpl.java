package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchCandidateResponse;
import com.example.PLAGIARISM_SERVICE.feign.ResearchFeignClient;
import com.example.PLAGIARISM_SERVICE.service.CandidateRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateRetrievalServiceImpl implements CandidateRetrievalService {

    private final ResearchFeignClient researchFeignClient;

    @Override
    public List<ResearchCandidateResponse> retrieveCandidates() {

        ApiResponse<List<ResearchCandidateResponse>> response =
                researchFeignClient.getPublishedResearches();

        if(response == null || !response.isSuccess() || response.getData() == null){

            return Collections.emptyList();
        }

        return response.getData();
    }
}