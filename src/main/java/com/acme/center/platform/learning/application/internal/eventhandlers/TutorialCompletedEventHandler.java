package com.acme.center.platform.learning.application.internal.eventhandlers;

import com.acme.center.platform.learning.domain.model.commands.UpdateStudentMetricsOnTutorialCompletedCommand;
import com.acme.center.platform.learning.domain.model.events.TutorialCompletedEvent;
import com.acme.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import com.acme.center.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.center.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.center.platform.learning.domain.services.StudentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TutorialCompletedEventHandler {
    private final StudentCommandService studentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public TutorialCompletedEventHandler(StudentCommandService studentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.studentCommandService = studentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    @EventListener(TutorialCompletedEvent.class)
    public void on(TutorialCompletedEvent event) {
        var getEnrollmentByIdQuery = new GetEnrollmentByIdQuery(event.getEnrollmentId());
        var enrollment = enrollmentQueryService.handle(getEnrollmentByIdQuery);
        if (enrollment.isPresent()) {
            var studentEnrollment = enrollment.get();
            var updateStudentMetricsOnTutorialCompletedCommand = new UpdateStudentMetricsOnTutorialCompletedCommand(
                    studentEnrollment.getAcmeStudentRecordId());
            studentCommandService.handle(updateStudentMetricsOnTutorialCompletedCommand);
        }

    }
}
