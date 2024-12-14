package com.scs.identity.mapper;

import org.mapstruct.Mapper;

import com.scs.identity.dto.request.ProfileCreationRequest;
import com.scs.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
