package com.scs.identity.service;

import java.util.HashSet;
import java.util.List;

import com.scs.identity.dto.request.ProfileCreationRequest;
import com.scs.identity.mapper.ProfileMapper;
import com.scs.identity.repository.httpclient.ProfileClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scs.identity.constant.PredefinedRole;
import com.scs.identity.dto.request.UserCreationRequest;
import com.scs.identity.dto.request.UserUpdateRequest;
import com.scs.identity.dto.response.UserResponse;
import com.scs.identity.entity.Role;
import com.scs.identity.entity.User;
import com.scs.identity.exception.AppException;
import com.scs.identity.exception.ErrorCode;
import com.scs.identity.mapper.UserMapper;
import com.scs.identity.repository.RoleRepository;
import com.scs.identity.repository.UserRepository;
import com.scs.identity.util.ErrorMessageUtilHolder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    ProfileMapper profileMapper;
    ProfileClient profileClient;

    @NonFinal
    @Value("${security.password.encoder.strength}")
    protected int STRENGTH_PASSWORDENCODER;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(STRENGTH_PASSWORDENCODER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage(ErrorMessageUtilHolder.getErrorMessageUtil().getMessage("USER_EXISTED"));
            throw new AppException(errorCode);
        }

        ProfileCreationRequest profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        profileClient.createProfile(profileRequest);

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage(ErrorMessageUtilHolder.getErrorMessageUtil().getMessage("USER_NOT_EXISTED"));
            return new AppException(errorCode);
        });

        return userMapper.toUserResponse(user);
    }
}
