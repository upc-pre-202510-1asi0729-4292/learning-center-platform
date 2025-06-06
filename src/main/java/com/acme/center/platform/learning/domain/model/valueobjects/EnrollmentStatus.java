package com.acme.center.platform.learning.domain.model.valueobjects;

/**
 * Enumeration representing the status of an enrollment in a course.
 * @summary
 * This enum defines the possible states of an enrollment. It is used to track the lifecycle of an enrollment request.
 * The possible statuses are:
 * - REQUESTED: The enrollment has been requested but not yet confirmed.
 * - CONFIRMED: The enrollment has been confirmed and the user is enrolled in the course.
 * - REJECTED: The enrollment request has been rejected, and the user is not enrolled in the course.
 * - CANCELED: The enrollment has been canceled by the user or the system, and the user is no longer enrolled in the course.
 * @since 1.0.0
 */
public enum EnrollmentStatus {
    REQUESTED,
    CONFIRMED,
    REJECTED,
    CANCELED
}
