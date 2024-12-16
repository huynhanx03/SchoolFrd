package com.scs.profile.controller;

import com.scs.profile.dto.response.ApiResponse;
import com.scs.profile.dto.request.ProfileCreateRequest;
import com.scs.profile.dto.response.UserProfileResponse;
import com.scs.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/my-profile")
    ApiResponse<UserProfileResponse> getMyProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getMyProfile())
                .build();
    }

    @GetMapping("/users/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userProfileService.getByUserId(userId))
                .build();
    }
}
