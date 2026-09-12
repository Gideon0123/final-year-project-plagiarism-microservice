package com.example.PLAGIARISM_SERVICE.utils;

import com.example.PLAGIARISM_SERVICE.entity.IdempotencyRecord;
import com.example.PLAGIARISM_SERVICE.enums.IdempotencyState;
import com.example.PLAGIARISM_SERVICE.enums.IdempotencyStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IdempotencyStateResolver {

    public IdempotencyState resolve(
            Optional<IdempotencyRecord> record,
            String fingerprint
    ) {
        if (record.isEmpty()) {
            return IdempotencyState.NOT_FOUND;
        }

        IdempotencyRecord existing = record.get();

        if (!existing.getFingerprint().equals(fingerprint)) {
            return IdempotencyState.FINGERPRINT_MISMATCH;
        }

        if (existing.getStatus() == IdempotencyStatus.PROCESSING) {
            return IdempotencyState.PROCESSING;
        }

        return IdempotencyState.COMPLETED;
    }

}
