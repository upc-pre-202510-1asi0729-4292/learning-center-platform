package com.acme.center.platform.learning.application.internal.commandservices;

import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.commands.AddTutorialToCourseLearningPathCommand;
import com.acme.center.platform.learning.domain.model.commands.CreateCourseCommand;
import com.acme.center.platform.learning.domain.model.commands.DeleteCourseCommand;
import com.acme.center.platform.learning.domain.model.commands.UpdateCourseCommand;
import com.acme.center.platform.learning.domain.services.CourseCommandService;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseCommandServiceImpl implements CourseCommandService {
    private final CourseRepository courseRepository;

    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Long handle(CreateCourseCommand command) {
        if(courseRepository.existsByTitle(command.title())) throw new IllegalArgumentException("Course already exists");
        var course = new Course(command);
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Error saving course: " + e.getMessage(), e);
        }
        return course.getId();
    }

    @Override
    public Optional<Course> handle(UpdateCourseCommand command) {
        if(courseRepository.existsByTitleAndIdIsNot(command.title(), command.courseId())) throw new IllegalArgumentException("Course already exists");
        var course = courseRepository.findById(command.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + command.courseId()));
        try {
            var updatedCourse = course.updateInformation(command.title(), command.description());
            courseRepository.save(updatedCourse);
            return Optional.of(updatedCourse);
        } catch (Exception e) {
            throw new RuntimeException("Error updating course: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteCourseCommand command) {
        verifyIfCourseExistsById(command.courseId());
        try {
            courseRepository.deleteById(command.courseId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting course: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(AddTutorialToCourseLearningPathCommand command) {
        verifyIfCourseExistsById(command.courseId());
        try {
            courseRepository.findById(command.courseId()).map( course -> {
                course.addTutorialToLearningPath(command.tutorialId());
                courseRepository.save(course);
                return course;
            });
        } catch (Exception e) {
            throw new RuntimeException("Error adding tutorial to course learning path: " + e.getMessage(), e);
        }
    }

    private void verifyIfCourseExistsById(Long courseId) {
        if(!courseRepository.existsById(courseId))
                throw new IllegalArgumentException("Course not found with id: " + courseId);
    }
}
