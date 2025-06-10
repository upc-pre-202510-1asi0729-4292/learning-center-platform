package com.acme.center.platform.learning.domain.model.valueobjects;

import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.entities.ProgressRecordItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class ProgressRecord {
    @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL)
    private List<ProgressRecordItem> progressRecordItems;

    public ProgressRecord() {
        this.progressRecordItems = List.of();
    }

    public void initializeProgressRecord(Enrollment enrollment, LearningPath learningPath) {
        if(learningPath.isEmpty()) return;
        TutorialId tutorialId = learningPath.getFirstTutorialInLearningPath();
        ProgressRecordItem progressRecordItem = new ProgressRecordItem(enrollment, tutorialId);
        this.progressRecordItems.add(progressRecordItem);
    }

    private ProgressRecordItem getProgressRecordItemWithTutorialId(TutorialId tutorialId) {
        return this.progressRecordItems.stream()
                .filter(item -> item.getTutorialId().equals(tutorialId))
                .findFirst()
                .orElse(null);
    }

    private boolean hasAnItemInProgress() {
        return this.progressRecordItems.stream()
                .anyMatch(ProgressRecordItem::isInProgress);
    }

    public void startTutorial(TutorialId tutorialId) {
        if(hasAnItemInProgress()) throw new IllegalStateException("There is already a tutorial in progress.");
        ProgressRecordItem progressRecordItem = getProgressRecordItemWithTutorialId(tutorialId);
        if(progressRecordItem == null) throw new IllegalStateException("There is no progress record item with id " + tutorialId.toString());
        if (progressRecordItem.isInProgressOrCompleted()) throw new IllegalStateException("The tutorial with id " + tutorialId.toString() + " is already in progress or completed.");
        progressRecordItem.start();
    }

    public void completeTutorial(TutorialId tutorialId, LearningPath learningPath) {
        ProgressRecordItem progressRecordItem = getProgressRecordItemWithTutorialId(tutorialId);
        if (progressRecordItem == null) throw new IllegalStateException("There is no progress record item with id " + tutorialId.toString());
        if (progressRecordItem.isNotStartedOrCompleted()) throw new IllegalStateException("The tutorial with id " + tutorialId.toString() + " is not started or already completed.");
        progressRecordItem.complete();
        if (learningPath.isLastTutorialInLearningPath(tutorialId)) return;
        TutorialId nextTutorialId = learningPath.getNextTutorialInLearningPath(tutorialId);
        if (nextTutorialId == null) throw new IllegalStateException("There is no next tutorial in the learning path after " + tutorialId.toString());
        ProgressRecordItem nextProgressRecordItem = new ProgressRecordItem(progressRecordItem.getEnrollment(), nextTutorialId);
        this.progressRecordItems.add(nextProgressRecordItem);
    }
}
