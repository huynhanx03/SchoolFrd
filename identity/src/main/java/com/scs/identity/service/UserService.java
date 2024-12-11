package com.scs.identity.service;

import com.scs.identity.dto.request.UserCreationRequest;
import com.scs.identity.dto.request.UserUpdateRequest;
import com.scs.identity.entity.User;
import com.scs.identity.exception.AppException;
import com.scs.identity.exception.ErrorCode;
import com.scs.identity.repository.UserRepository;
import com.scs.identity.util.ErrorMessageUtilHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage(ErrorMessageUtilHolder.getErrorMessageUtil().getMessage("USER_EXISTED"));
            throw new AppException(errorCode);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}