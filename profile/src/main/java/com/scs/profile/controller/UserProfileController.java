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
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreateRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.createProfile(request))
                .build();
    }

    @GetMapping("/{userProfileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userProfileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getProfile(userProfileId))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .data(userProfileService.getAllProfiles())
                .build();
    }
}
