package com.scs.identity.mapper;

import com.scs.identity.dto.request.PermissionRequest;
import com.scs.identity.dto.request.UserCreationRequest;
import com.scs.identity.dto.request.UserUpdateRequest;
import com.scs.identity.dto.response.PermissionResponse;
import com.scs.identity.dto.response.UserResponse;
import com.scs.identity.entity.Permission;
import com.scs.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
