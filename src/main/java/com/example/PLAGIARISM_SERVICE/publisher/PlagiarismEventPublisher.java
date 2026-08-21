package com.example.PLAGIARISM_SERVICE.publisher;

import com.example.PLAGIARISM_SERVICE.entity.PlagiarismCheck;

public interface PlagiarismEventPublisher {

    void publishCompleted(PlagiarismCheck check);
}