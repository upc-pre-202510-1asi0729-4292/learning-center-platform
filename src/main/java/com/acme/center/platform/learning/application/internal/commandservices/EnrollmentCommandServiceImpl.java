package com.acme.center.platform.learning.application.internal.commandservices;

import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.commands.*;
import com.acme.center.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseRepository;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentCommandServiceImpl(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Long handle(RequestEnrollmentCommand command) {
        if(!studentRepository.existsByAcmeStudentRecordId(command.studentRecordId()))
            throw new IllegalArgumentException("Student does not exist with record ID: " + command.studentRecordId());
        var course = courseRepository.findById(command.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + command.courseId()));
        try {
            var enrollment = new Enrollment(command.studentRecordId(), course);
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        } catch (Exception e) {
            throw new RuntimeException("Error saving enrollment: " + e.getMessage(), e);
        }
    }

    @Override
    public Long handle(ConfirmEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.confirm();
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + command.enrollmentId()));
        return null;
    }

    @Override
    public Long handle(RejectEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.reject();
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + command.enrollmentId()));
        return null;
    }

    @Override
    public Long handle(CancelEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.cancel();
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + command.enrollmentId()));
        return null;
    }

    @Override
    public Long handle(CompleteTutorialForEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.completeTutorial(command.tutorialId());
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + command.enrollmentId()));
        return null;
    }
}
