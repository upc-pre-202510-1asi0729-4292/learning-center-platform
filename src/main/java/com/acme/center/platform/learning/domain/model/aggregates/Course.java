package com.acme.center.platform.learning.domain.model.aggregates;

import com.acme.center.platform.shared.domain.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

/**
 * Course aggregate root.
 * @summary
 * This class represents a course in the learning platform.
 * It contains information about the course such as title and description.
 * It extends from AuditableAbstractAggregateRoot to provide auditing capabilities.
 */
@Getter
@Entity
public class Course extends AuditableAbstractAggregateRoot<Course> {
    private String title;
    private String description;

    public Course() {
        this.title = Strings.EMPTY;
        this.description = Strings.EMPTY;
    }

    /**
     * Update the course information.
     * @param title the new title of the course
     * @param description the new description of the course
     * @return the updated Course object
     */
    public Course updateInformation(String title, String description) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        return this;
    }

}
