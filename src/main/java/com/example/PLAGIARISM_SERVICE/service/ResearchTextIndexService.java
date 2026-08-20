package com.example.PLAGIARISM_SERVICE.service;

import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;

import java.util.List;

public interface ResearchTextIndexService {

    ResearchTextIndex createIndex(
            Long paperId
    );

    ResearchTextIndex getByPaperId(
            Long paperId
    );

    void rebuildIndex(
            Long paperId
    );

    List<ResearchTextIndex> getAllCandidates(
            Long excludedPaperId
    );
}