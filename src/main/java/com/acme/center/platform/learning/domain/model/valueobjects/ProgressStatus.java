package com.acme.center.platform.learning.domain.model.valueobjects;

/**
 * Enumeration representing the status of an enrollment in a course.
 * @summary
 * This enum defines the possible states of an enrollment. It is used to track the status of the progress of a learner in a course.
 * The possible values are:
 * - NO_STARTED: The learner has not started the course yet.
 * - STARTED: The learner has started the course but has not completed it yet.
 * - COMPLETED: The learner has completed the course.
 * @since 1.0.0
 */
public enum ProgressStatus {
    NO_STARTED,
    STARTED,
    COMPLETED
}
