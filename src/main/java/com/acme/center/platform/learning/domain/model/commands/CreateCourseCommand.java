package com.acme.center.platform.learning.domain.model.commands;

public record CreateCourseCommand(String title, String description) {

    public CreateCourseCommand {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be null or blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must not be null or blank.");
        }
    }
}
