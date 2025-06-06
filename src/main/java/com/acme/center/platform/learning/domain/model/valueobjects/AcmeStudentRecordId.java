package com.acme.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

/**
 * Value Object representing a unique identifier for a student record.
 * @summary
 * Represents a unique identifier for a student record in the Acme Learning Platform.
 * This ID is used to reference student records within the system.
 * @param studentRecordId the unique identifier for the student record.
 * @see IllegalArgumentException
 * @since 1.0.0
 */
@Embeddable
public record AcmeStudentRecordId(String studentRecordId) {

    public AcmeStudentRecordId() {
        this(UUID.randomUUID().toString());
    }

    public AcmeStudentRecordId {
        if (studentRecordId == null || studentRecordId.isBlank()) {
            throw new IllegalArgumentException("Student record ID cannot be null or blank");
        }
    }
}
