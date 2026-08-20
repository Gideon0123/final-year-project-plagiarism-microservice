package com.example.PLAGIARISM_SERVICE.service.impl;

import com.example.PLAGIARISM_SERVICE.service.FileDownloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDownloadServiceImpl implements FileDownloadService {

    private final RestClient restClient;

    public FileDownloadServiceImpl(
            RestClient.Builder restClientBuilder
    ) {
        this.restClient = restClientBuilder.build();
    }

    @Override
    public byte[] downloadFile(
            String downloadUrl
    ) {
        if (downloadUrl == null || downloadUrl.isBlank()) {

            throw new IllegalArgumentException(
                    "Download URL cannot be null or empty"
            );
        }

        try {
            byte[] file = restClient.get()
                    .uri(downloadUrl)
                    .retrieve()
                    .body(byte[].class);

            if (file == null || file.length == 0) {
                throw new IllegalStateException(
                        "Downloaded file is empty"
                );
            }

            log.info(
                    "Successfully downloaded file. Size={} bytes",
                    file.length
            );

            return file;

        } catch (Exception ex) {

            log.error(
                    "Failed to download file from {}",
                    downloadUrl,
                    ex
            );

            throw new IllegalStateException(
                    "Failed to download research file",
                    ex
            );
        }
    }
}