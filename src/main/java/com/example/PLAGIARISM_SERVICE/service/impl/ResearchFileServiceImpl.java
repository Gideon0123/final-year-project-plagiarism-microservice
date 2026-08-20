package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchFileResponse;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.feign.ResearchFileClient;
import com.example.PLAGIARISM_SERVICE.service.FileDownloadService;
import com.example.PLAGIARISM_SERVICE.service.ResearchFileService;
import com.example.PLAGIARISM_SERVICE.service.TextExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResearchFileServiceImpl implements ResearchFileService {

    private final ResearchFileClient researchFileClient;
    private final FileDownloadService fileDownloadService;
    private final TextExtractionService textExtractionService;

    @Override
    public ExtractedTextResponse retrieveAndExtract(
            Long paperId
    ) {
        log.info(
                "Starting file retrieval for paperId={}",
                paperId
        );

        /*
         * STEP 1
         * Retrieve file metadata.
         */
        ApiResponse<ResearchFileResponse> response =
                researchFileClient.getResearchFileMetadata(
                        paperId
                );

        if (response == null || !response.isSuccess() || response.getData() == null) {

            throw new ResourceNotFoundException(
                    "Research file metadata not found for paper: "
                            + paperId
            );
        }


        ResearchFileResponse metadata = response.getData();

        /*
         * STEP 2
         * Validate metadata.
         */
        validateFile(metadata);

        /*
         * STEP 3
         * Download actual bytes.
         */
        byte[] fileBytes = fileDownloadService.downloadResearchFile(
                paperId
        );

        /*
         * STEP 4
         * Extract text.
         */
        ExtractedTextResponse extractedText = textExtractionService.extractText(
                fileBytes,
                metadata.fileName()
        );

        log.info(
                "Successfully retrieved and extracted paperId={}",
                paperId
        );


        return extractedText;
    }


    private void validateFile(
            ResearchFileResponse file
    ) {
        if (file.fileName() == null || file.fileName().isBlank()) {

            throw new IllegalStateException(
                    "Research file does not have a file name"
            );
        }

        String fileName = file.fileName().toLowerCase();

        if (!fileName.endsWith(".pdf") && !fileName.endsWith(".docx")) {

            throw new IllegalArgumentException(
                    "Unsupported research file format: " + file.fileName()
            );
        }

        if (file.fileSize() == null || file.fileSize() <= 0) {

            throw new IllegalStateException(
                    "Research file has an invalid file size"
            );
        }
    }
}