package com.scs.identity.mapper;

import org.mapstruct.Mapper;

import com.scs.identity.dto.request.PermissionRequest;
import com.scs.identity.dto.response.PermissionResponse;
import com.scs.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
