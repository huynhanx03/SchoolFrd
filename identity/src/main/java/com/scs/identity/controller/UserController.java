package com.scs.identity.controller;

import com.scs.identity.dto.request.ApiResponse;
import com.scs.identity.dto.request.UserCreationRequest;
import com.scs.identity.dto.request.UserUpdateRequest;
import com.scs.identity.dto.response.UserResponse;
import com.scs.identity.entity.User;
import com.scs.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setData(userService.createUser(request));

        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<User>> getUsers() {
        return ApiResponse.<List<User>>builder()
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(userId))
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("{userId}")
    ApiResponse<Void> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }
}
