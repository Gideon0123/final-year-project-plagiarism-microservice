package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.dto.ApiResponse;
import com.example.PLAGIARISM_SERVICE.dto.ExtractedTextResponse;
import com.example.PLAGIARISM_SERVICE.dto.ResearchFileResponse;
import com.example.PLAGIARISM_SERVICE.exceptions.ResourceNotFoundException;
import com.example.PLAGIARISM_SERVICE.feign.ResearchFeignClient;
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

    private final ResearchFeignClient researchFeignClient;
    private final FileDownloadService fileDownloadService;
    private final TextExtractionService textExtractionService;

    @Override
    public ExtractedTextResponse retrieveAndExtract(
            Long paperId
    ) {
        log.info(
                "Retrieving research file for paperId={}",
                paperId
        );

        /*
         * Step 1:
         * Ask Research Service for file information.
         */
        ApiResponse<ResearchFileResponse> response =
                researchFeignClient.downloadResearchFile(
                        paperId
                );

        if (response == null || !response.isSuccess() || response.getData() == null) {

            throw new ResourceNotFoundException(
                    "Research file information not found for paper: "
                            + paperId
            );
        }

        ResearchFileResponse file = response.getData();

        /*
         * Step 2:
         * Validate the file information.
         */
        validateFile(file);

        /*
         * Step 3:
         * Download the actual file.
         */
        byte[] fileBytes =
                fileDownloadService.downloadFile(
                        file.downloadUrl()
                );

        /*
         * Step 4:
         * Extract text.
         */
        ExtractedTextResponse extractedText =
                textExtractionService.extractText(
                        fileBytes,
                        file.fileName()
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

        if (file.downloadUrl() == null || file.downloadUrl().isBlank()) {

            throw new IllegalStateException(
                    "Research file does not have a download URL"
            );
        }

        String fileName = file.fileName().toLowerCase();

        if (!fileName.endsWith(".pdf") &&
                !fileName.endsWith(".docx")) {

            throw new IllegalArgumentException(
                    "Unsupported research file format: "
                            + file.fileName()
            );
        }
    }
}