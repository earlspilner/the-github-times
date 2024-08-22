package org.earlspilner.users.service;

import org.earlspilner.users.model.Profile;
import org.earlspilner.users.rest.dto.request.ProfileRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Alexander Dudkin
 */
public interface ProfileService {
    Page<Profile> getProfiles(Pageable pageable);
    List<Profile> getProfiles(Sort sort);
    List<Profile> getProfiles();
    Profile getProfileById(int id);
    Profile createProfile(ProfileRequest dto);
    Profile updateProfile(int id, ProfileRequest dto);
    void deleteProfileById(int id);
}