package com.scs.profile.controller;

import com.scs.profile.dto.request.ProfileCreateRequest;
import com.scs.profile.dto.response.ApiResponse;
import com.scs.profile.dto.response.UserProfileResponse;
import com.scs.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping
    UserProfileResponse createProfile(@RequestBody ProfileCreateRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/{userProfileId}")
    UserProfileResponse getProfile(@PathVariable String userProfileId) {
        return userProfileService.getProfile(userProfileId);
    }

    @GetMapping
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .data(userProfileService.getAllProfiles())
                .build();
    }
}
