package com.acme.center.platform.learning.domain.model.queries;

public record GetLearningPathItemByCourseIdAndTutorialIdQuery(Long courseId, Long tutorialId) {

    public GetLearningPathItemByCourseIdAndTutorialIdQuery {
        if (courseId == null || courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be a positive number.");
        }
        if (tutorialId == null || tutorialId <= 0) {
            throw new IllegalArgumentException("Tutorial ID must be a positive number.");
        }
    }
}
