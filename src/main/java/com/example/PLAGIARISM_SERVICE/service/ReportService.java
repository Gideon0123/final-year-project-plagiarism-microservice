package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.dto.PlagiarismReportResponse;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;
import com.example.PLAGIARISM_SERVICE.entity.PlagiarismMatch;

import java.util.List;

public interface ReportService {

    String generateReport(
            PlagiarismCheck check, List<PlagiarismMatch> matches
    );

    PlagiarismReportResponse getReport(Long checkId);

    byte[] downloadReport(Long checkId);

}