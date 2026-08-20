package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.ResearchCandidateResponse;

import java.util.List;

public interface CandidateRetrievalService {

    List<ResearchCandidateResponse> retrieveCandidates();
}