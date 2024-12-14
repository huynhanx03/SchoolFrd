package com.scs.profile.service;

import com.scs.profile.dto.request.ProfileCreateRequest;
import com.scs.profile.dto.response.UserProfileResponse;
import com.scs.profile.entity.UserProfile;
import com.scs.profile.mapper.UserProfileMapper;
import com.scs.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreateRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);

        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("User profile not found"));

        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getAllProfiles() {
        var profiles = userProfileRepository.findAll();

        return profiles.stream().map(userProfileMapper::toUserProfileResponse).toList();
    }
}
