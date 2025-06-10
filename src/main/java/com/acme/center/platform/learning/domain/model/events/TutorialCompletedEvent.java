package com.acme.center.platform.learning.domain.model.events;

import com.acme.center.platform.learning.domain.model.valueobjects.TutorialId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TutorialCompletedEvent extends ApplicationEvent {
    private final Long enrollmentId;
    private final TutorialId tutorialId;

    public TutorialCompletedEvent(Object source, Long enrollmentId, TutorialId tutorialId) {
        super(source);
        this.enrollmentId = enrollmentId;
        this.tutorialId = tutorialId;
    }
}
