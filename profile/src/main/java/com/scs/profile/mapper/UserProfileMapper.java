package com.scs.profile.mapper;

import com.scs.profile.dto.request.ProfileCreateRequest;
import com.scs.profile.dto.response.UserProfileResponse;
import com.scs.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreateRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}
