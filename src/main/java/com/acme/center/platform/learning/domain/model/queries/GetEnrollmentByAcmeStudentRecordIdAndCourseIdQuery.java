package com.acme.center.platform.learning.domain.model.queries;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public record GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery(AcmeStudentRecordId studentRecordId, Long courseId) {

    public GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery {
        if (studentRecordId == null || studentRecordId.studentRecordId() == null || studentRecordId.studentRecordId().isBlank()) {
            throw new IllegalArgumentException("Student record ID must not be null or blank.");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must not be null or less than or equal to zero.");
        }
    }
}
