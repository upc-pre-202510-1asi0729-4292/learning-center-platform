package com.acme.center.platform.learning.domain.model.queries;

public record GetAllEnrollmentsByCourseIdQuery(Long courseId) {

    public GetAllEnrollmentsByCourseIdQuery {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive number.");
        }
    }
}
