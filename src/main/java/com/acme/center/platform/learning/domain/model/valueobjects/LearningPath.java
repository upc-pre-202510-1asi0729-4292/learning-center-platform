package com.acme.center.platform.learning.domain.model.valueobjects;

import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.entities.LearningPathItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Embeddable
public class LearningPath {
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<LearningPathItem> learningPathItems;

    public LearningPath() {
        this.learningPathItems = List.of();
    }

    private LearningPathItem getFirstLearningPathItemWhere(Predicate<LearningPathItem> predicate) {
        return this.learningPathItems.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public LearningPathItem getLearningPathItemWithTutorialId(TutorialId tutorialId) {
        return this.getFirstLearningPathItemWhere(
                item -> item.getTutorialId().equals(tutorialId));
    }

    public TutorialId getNextTutorialInLearningPath(TutorialId currentTutorialId) {
        LearningPathItem nextItem = this.getLearningPathItemWithTutorialId(currentTutorialId).getNextItem();
        return !Objects.isNull(nextItem) ? nextItem.getTutorialId() : null;
    }

    public boolean isLastTutorialInLearningPath(TutorialId currentTutorialId) {
        return Objects.isNull(getNextTutorialInLearningPath(currentTutorialId));
    }

    public TutorialId getFirstTutorialInLearningPath() {
        return this.learningPathItems.getFirst().getTutorialId();
    }

    public LearningPathItem getLastItemInLearningPath() {
        return this.getFirstLearningPathItemWhere(item -> Objects.isNull(item.getNextItem()));
    }

    public boolean isEmpty() {
        return this.learningPathItems.isEmpty();
    }

    public void addItem(Course course, TutorialId tutorialId, LearningPathItem nextItem) {
        LearningPathItem learningPathItem = new LearningPathItem(course, tutorialId, nextItem);
        learningPathItems.add(learningPathItem);
    }

    public void addItem(Course course, TutorialId tutorialId) {
        LearningPathItem learningPathItem = new LearningPathItem(course, tutorialId, null);
        LearningPathItem originalLastItem = null;
        if(!isEmpty()) originalLastItem = getLastItemInLearningPath();
        learningPathItems.add(learningPathItem);
        if(!Objects.isNull(originalLastItem)) originalLastItem.updateNextItem(learningPathItem);
    }

    public void addItem(Course course, TutorialId tutorialId,TutorialId nextTutorialId) {
        LearningPathItem nextItem = this.getLearningPathItemWithTutorialId(nextTutorialId);
        addItem(course, tutorialId, nextItem);
    }
}
