package com.example.PLAGIARISM_SERVICE.service;

public interface FileDownloadService {

    byte[] downloadFile(
            String downloadUrl
    );
}