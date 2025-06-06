package com.acme.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing Student Performance Metrics.
 * @summary
 * This class encapsulates the performance metrics of a student, including the total number of completed courses and tutorials.
 * It provides methods to increase these metrics and ensures that they are non-negative integers.
 * @param totalCompletedCourses the total number of courses completed by the student.
 * @param totalCompletedTutorials the total number of tutorials completed by the student.
 * @see IllegalArgumentException
 * @since 1.0.0
 */
@Embeddable
public record StudentPerformanceMetricSet(Integer totalCompletedCourses, Integer totalCompletedTutorials) {
    public StudentPerformanceMetricSet() {
        this(0, 0);
    }

    public StudentPerformanceMetricSet {
        if (totalCompletedCourses == null || totalCompletedCourses < 0) {
            throw new IllegalArgumentException("Total completed courses must be a non-negative integer.");
        }
        if (totalCompletedTutorials == null || totalCompletedTutorials < 0) {
            throw new IllegalArgumentException("Total completed tutorials must be a non-negative integer.");
        }
    }

    /**
     * Increments the count of completed courses by one.
     * @summary
     * This method creates a new instance of StudentPerformanceMetricSet with the totalCompletedCourses incremented by one.
     * @return a new StudentPerformanceMetricSet instance with the updated count of completed courses.
     * @since 1.0.0
     */
    public StudentPerformanceMetricSet incrementCompletedCourses() {
        return new StudentPerformanceMetricSet(totalCompletedCourses + 1, totalCompletedTutorials);
    }

    /**
     * Increments the count of completed tutorials by one.
     * @summary
     * This method creates a new instance of StudentPerformanceMetricSet with the totalCompletedTutorials incremented by one.
     * @return a new StudentPerformanceMetricSet instance with the updated count of completed tutorials.
     * @since 1.0.0
     */
    public StudentPerformanceMetricSet incrementCompletedTutorials() {
        return new StudentPerformanceMetricSet(totalCompletedCourses, totalCompletedTutorials + 1);
    }
}
