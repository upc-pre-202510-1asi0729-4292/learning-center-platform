package com.acme.center.platform.learning.domain.model.commands;

public record DeleteCourseCommand(Long courseId) {

    public DeleteCourseCommand {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive number.");
        }
    }
}
