package com.scs.identity.controller;

import com.scs.identity.dto.request.ApiResponse;
import com.scs.identity.dto.request.PermissionRequest;
import com.scs.identity.dto.request.UserCreationRequest;
import com.scs.identity.dto.request.UserUpdateRequest;
import com.scs.identity.dto.response.PermissionResponse;
import com.scs.identity.dto.response.UserResponse;
import com.scs.identity.entity.User;
import com.scs.identity.service.PermissionService;
import com.scs.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.create(request))
                .build();
    }

    @GetMapping
    List<PermissionResponse> getAll () {
        return permissionService.getAll();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable("permission") String permission) {
        permissionService.delete(permission);

        return ApiResponse.<Void>builder()
                .build();
    }
}
