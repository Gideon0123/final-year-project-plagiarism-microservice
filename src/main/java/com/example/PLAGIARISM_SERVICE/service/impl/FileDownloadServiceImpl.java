package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.exceptions.FileStorageException;
import com.example.PLAGIARISM_SERVICE.feign.ResearchFileClient;
import com.example.PLAGIARISM_SERVICE.service.FileDownloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDownloadServiceImpl implements FileDownloadService {

    private final ResearchFileClient researchFileClient;


    @Override
    public byte[] downloadResearchFile(
            Long paperId
    ) {

        log.info(
                "Requesting research file from Research Service, paperId={}",
                paperId
        );

        ResponseEntity<byte[]> response =
                researchFileClient.downloadResearchFile(
                        paperId
                );

        if (response == null) {
            throw new FileStorageException(
                    "Research Service returned no response"
            );
        }

        if (!response.getStatusCode().is2xxSuccessful()) {

            throw new FileStorageException(
                    "Failed to download research file. HTTP status: "
                            + response.getStatusCode()
            );
        }

        byte[] body = response.getBody();

        if (body == null || body.length == 0) {

            throw new FileStorageException(
                    "Research Service returned an empty file"
            );
        }

        log.info(
                "Successfully downloaded research file, paperId={}, size={} bytes",
                paperId,
                body.length
        );

        return body;
    }
}