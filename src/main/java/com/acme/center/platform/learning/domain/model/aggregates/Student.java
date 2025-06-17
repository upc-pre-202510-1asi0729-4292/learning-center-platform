package com.acme.center.platform.learning.domain.model.aggregates;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;
import com.acme.center.platform.learning.domain.model.valueobjects.StudentPerformanceMetricSet;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class Student extends AuditableAbstractAggregateRoot<Student> {
    @Getter
    @Embedded
    @Column(name = "acme_student_id")
    private AcmeStudentRecordId acmeStudentRecordId;

    @Embedded
    private ProfileId profileId;

    @Embedded
    private StudentPerformanceMetricSet performanceMetricSet;

    public Student() {
        // Default constructor for JPA
        this.acmeStudentRecordId = new AcmeStudentRecordId();
        this.performanceMetricSet = new StudentPerformanceMetricSet();
    }

    public Student(ProfileId profileId) {
        this();
        this.profileId = profileId;
    }

    public void updateMetricsOnTutorialCompleted() {
        this.performanceMetricSet = this.performanceMetricSet.incrementCompletedTutorials();
    }

    public void updateMetricsOnCourseCompleted() {
        this.performanceMetricSet = this.performanceMetricSet.incrementCompletedCourses();
    }

    public String getStudentRecordId() {
        return this.acmeStudentRecordId.studentRecordId();
    }

    public Long getProfileId() {
        return this.profileId.profileId();
    }

    public int getTotalCompletedCourses() {
        return this.performanceMetricSet.totalCompletedCourses();
    }

    public int getTotalCompletedTutorials() {
        return this.performanceMetricSet.totalCompletedTutorials();
    }
}
