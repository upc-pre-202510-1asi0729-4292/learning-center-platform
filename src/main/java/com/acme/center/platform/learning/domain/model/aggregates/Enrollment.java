package com.acme.center.platform.learning.domain.model.aggregates;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.EnrollmentStatus;
import com.acme.center.platform.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
public class Enrollment extends AuditableAbstractAggregateRoot<Enrollment> {

    @Getter
    @Embedded
    private AcmeStudentRecordId acmeStudentRecordId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private EnrollmentStatus status;

    protected Enrollment() {
        // Default constructor for JPA
    }

    public Enrollment(AcmeStudentRecordId acmeStudentRecordId, Course course) {
        this.acmeStudentRecordId = acmeStudentRecordId;
        this.course = course;
        this.status = EnrollmentStatus.REQUESTED;
        // this.addDomainEvent(new EnrollmentRequestedEvent(this));
    }

    public void confirm() {
        this.status = EnrollmentStatus.CONFIRMED;
        // this.addDomainEvent(new EnrollmentConfirmedEvent(this));
    }

    public void reject() {
        this.status = EnrollmentStatus.REJECTED;
        // this.addDomainEvent(new EnrollmentRejectedEvent(this));
    }
    public void cancel() {
        this.status = EnrollmentStatus.CANCELED;
        // this.addDomainEvent(new EnrollmentCanceledEvent(this));
    }

    public String getStatus() {
        return status.name().toLowerCase();
    }

    public boolean isConfirmed() {
        return EnrollmentStatus.CONFIRMED.equals(this.status);
    }

    public boolean isRejected() {
        return EnrollmentStatus.REJECTED.equals(this.status);
    }

    public boolean isCanceled() {
        return EnrollmentStatus.CANCELED.equals(this.status);
    }
    public boolean isRequested() {
        return EnrollmentStatus.REQUESTED.equals(this.status);
    }
}
