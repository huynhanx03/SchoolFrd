package com.scs.identity.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.scs.identity.dto.request.ApiResponse;
import com.scs.identity.dto.request.PermissionRequest;
import com.scs.identity.dto.response.PermissionResponse;
import com.scs.identity.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
    List<PermissionResponse> getAll() {
        return permissionService.getAll();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable("permission") String permission) {
        permissionService.delete(permission);

        return ApiResponse.<Void>builder().build();
    }
}
