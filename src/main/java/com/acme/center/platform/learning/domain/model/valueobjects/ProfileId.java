package com.acme.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing a Profile ID.
 * @summary
 * Represents a unique identifier for a user profile.
 * This class is immutable and ensures that the profile ID is always a positive number.
 * @param profileId the unique identifier for the profile that must be a positive number.
 * @see IllegalArgumentException
 * @since 1.0.0
 */
@Embeddable
public record ProfileId(Long profileId) {

    public ProfileId {
        if (profileId == null || profileId <= 0) {
            throw new IllegalArgumentException("Profile ID must be a positive number.");
        }
    }

}
