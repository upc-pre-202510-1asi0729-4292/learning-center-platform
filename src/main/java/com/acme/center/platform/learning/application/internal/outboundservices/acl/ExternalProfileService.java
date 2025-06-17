package com.acme.center.platform.learning.application.internal.outboundservices.acl;

import com.acme.center.platform.learning.domain.model.valueobjects.ProfileId;
import com.acme.center.platform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * External Profile Service
 */
@Service
public class ExternalProfileService {
    private final ProfilesContextFacade profilesContextFacade;

    /**
     * Constructor
     *
     * @param profilesContextFacade Profiles Context Facade
     */
    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    /**
     * Fetch Profile By Email
     * @param email The email of the profile
     * @return An {@link Optional} of {@link ProfileId}
     */
    public Optional<ProfileId> fetchProfileByEmail(String email) {
        var profileId = profilesContextFacade.fetchProfileIdByEmail(email);
        return profileId == 0L ? Optional.empty() : Optional.of(new ProfileId(profileId));
    }

    /**
     * Create Profile
     * @param firstName The first name of the profile
     * @param lastName The last name of the profile
     * @param email The email of the profile
     * @param street The street address of the profile
     * @param number The street number of the profile
     * @param city The city of the profile
     * @param postalCode The postal code of the profile
     * @param country The country of the profile
     * @return An {@link Optional} of {@link ProfileId}
     */
    public Optional<ProfileId> createProfile(
            String firstName,
            String lastName,
            String email,
            String street,
            String number,
            String city,
            String postalCode,
            String country) {
        var profileId = profilesContextFacade.createProfile(firstName, lastName, email, street, number, city, postalCode, country);
        return profileId == 0L ? Optional.empty() : Optional.of(new ProfileId(profileId));
    }
}
