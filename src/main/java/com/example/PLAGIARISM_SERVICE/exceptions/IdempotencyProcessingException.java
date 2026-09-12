package com.example.PLAGIARISM_SERVICE.exceptions;

public class IdempotencyProcessingException extends RuntimeException {
    public IdempotencyProcessingException(String message) {
        super(message);
    }
}
