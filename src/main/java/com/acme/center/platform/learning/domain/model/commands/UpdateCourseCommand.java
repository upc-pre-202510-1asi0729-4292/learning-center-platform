package com.acme.center.platform.learning.domain.model.commands;

public record UpdateCourseCommand(Long courseId, String title, String description) {

    public UpdateCourseCommand {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive number.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be null or blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must not be null or blank.");
        }
    }
}
