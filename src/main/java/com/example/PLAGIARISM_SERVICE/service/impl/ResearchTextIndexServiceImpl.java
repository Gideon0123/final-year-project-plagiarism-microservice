package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.dto.PreprocessedText;
import com.example.PLAGIARISM_SERVICE.dto.ResearchPaperResponse;
import com.example.PLAGIARISM_SERVICE.entity.ResearchTextIndex;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.repository.ResearchTextIndexRepository;
import com.example.PLAGIARISM_SERVICE.service.ResearchClientService;
import com.example.PLAGIARISM_SERVICE.service.ResearchFileService;
import com.example.PLAGIARISM_SERVICE.service.ResearchTextIndexService;
import com.example.PLAGIARISM_SERVICE.service.TextPreprocessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchTextIndexServiceImpl implements ResearchTextIndexService {

    private final ResearchClientService researchClientService;
    private final ResearchFileService researchFileService;
    private final TextPreprocessingService preprocessingService;
    private final ResearchTextIndexRepository repository;

    @Override
    @Transactional
    public ResearchTextIndex createIndex(
            Long paperId
    ) {
        if(repository.existsByPaperId(paperId)) {
            return repository.findByPaperId(paperId
            ).orElseThrow();
        }

        ResearchPaperResponse paper =
                researchClientService.getPaper(
                        paperId
                );

        ExtractedTextResponse extracted = researchFileService.retrieveAndExtract(
                paperId
        );

        PreprocessedText preprocessed = preprocessingService.preprocess(
                extracted.text()
        );

        ResearchTextIndex index =
                ResearchTextIndex.builder()
                        .paperId(paperId)
                        .title(paper.title())
                        .authorId(paper.authorId())
                        .rawText(extracted.text())
                        .normalizedText(
                                preprocessed.normalizedText()
                        )
                        .tokenCount(
                                preprocessed.tokens().size()
                        )
                        .indexedAt(LocalDateTime.now())
                        .build();

        return repository.save(index);
    }

    @Override
    @Transactional(readOnly = true)
    public ResearchTextIndex getByPaperId(
            Long paperId
    ) {
        return repository.findByPaperId(paperId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Indexed research not found for paper id: " + paperId
                )
        );
    }

    @Override
    @Transactional
    public void rebuildIndex(
            Long paperId
    ) {
        repository.findByPaperId(paperId).ifPresent(repository::delete);
        createIndex(paperId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResearchTextIndex> getAllCandidates(
            Long excludedPaperId
    ) {
        return repository.findAllByPaperIdNot(
                excludedPaperId
        );
    }
}
