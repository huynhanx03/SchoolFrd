package com.scs.identity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scs.identity.dto.request.PermissionRequest;
import com.scs.identity.dto.response.PermissionResponse;
import com.scs.identity.entity.Permission;
import com.scs.identity.mapper.PermissionMapper;
import com.scs.identity.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permissionID) {
        permissionRepository.deleteById(permissionID);
    }
}
