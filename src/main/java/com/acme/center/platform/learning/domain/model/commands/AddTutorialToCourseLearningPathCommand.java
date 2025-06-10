package com.acme.center.platform.learning.domain.model.commands;

import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;

public record AddTutorialToCourseLearningPathCommand(TutorialId tutorialId, Long courseId) {

    public AddTutorialToCourseLearningPathCommand {
        if (tutorialId == null || tutorialId.tutorialId() <= 0) {
            throw new IllegalArgumentException("Tutorial ID cannot be null or negative");
        }
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive number");
        }
    }
}
