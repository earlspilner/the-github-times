package org.raddan.newspaper.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.raddan.newspaper.entity.Profile;
import org.raddan.newspaper.entity.dto.ProfileRequest;
import org.raddan.newspaper.entity.response.creation.ProfileCreationResponse;
import org.raddan.newspaper.entity.response.info.ProfileInfoResponse;
import org.raddan.newspaper.exception.AlreadyExistsException;
import org.raddan.newspaper.filter.DateFilter;
import org.raddan.newspaper.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;
    private final UserService userService;

    public ProfileCreationResponse createProfile(ProfileRequest request) {
        Optional<Profile> optionalProfile = profileRepository.findByUser(userService.getCurrentUser().getId());
        if (optionalProfile.isPresent()) {
            throw new AlreadyExistsException("You already have an existing profile..");
        }

        var profile = Profile.builder()
                .user(userService.getCurrentUser())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .bio(request.getBio())
                .createdUtc(Instant.now().getEpochSecond())
                .updatedUtc(Instant.now().getEpochSecond())
                .build();

        profileRepository.save(profile);

        return new ProfileCreationResponse(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                DateFilter.formatInstant(profile.getCreatedUtc())
        );

    }

    public ProfileInfoResponse getProfileInfo(String username) {
        Optional<Profile> optionalProfile = profileRepository.findByUsername(username);

        if (optionalProfile.isEmpty())
            throw new EntityNotFoundException("Profile not found..");

        return createProfileInfoResponse(optionalProfile.get());
    }

    public ProfileInfoResponse editProfileInfo(ProfileRequest request) {
        Optional<Profile> optionalProfile = profileRepository.findByUser(userService.getCurrentUser().getId());
        if (optionalProfile.isEmpty())
            throw new EntityNotFoundException("Profile not found..");

        var profile = optionalProfile.get();

        if (request.getFirstName() != null)
            profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            profile.setLastName(request.getLastName());
        if (request.getBio() != null)
            profile.setBio(request.getBio());

        profile.setUpdatedUtc(Instant.now().getEpochSecond());
        profileRepository.save(profile);
        return createProfileInfoResponse(profile);
    }

    private ProfileInfoResponse createProfileInfoResponse(Profile profile) {
        return new ProfileInfoResponse(
                profile.getId(),
                profile.getUser().getUsername(),
                profile.getUser().getEmail(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBio(),
                DateFilter.formatInstant(profile.getCreatedUtc()),
                DateFilter.formatInstant(profile.getUpdatedUtc())
                );
    }
}
