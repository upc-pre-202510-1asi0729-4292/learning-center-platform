package com.acme.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record TutorialId(Long tutorialId) {
    public TutorialId {
        if (tutorialId == null || tutorialId <= 0) {
            throw new IllegalArgumentException("Tutorial ID must be a positive number.");
        }
    }
}
