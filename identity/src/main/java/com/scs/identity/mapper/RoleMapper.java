package com.scs.identity.mapper;

import com.scs.identity.dto.request.RoleRequest;
import com.scs.identity.dto.response.RoleResponse;
import com.scs.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
