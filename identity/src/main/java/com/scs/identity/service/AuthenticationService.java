package com.scs.identity.service;

import com.scs.identity.dto.request.AuthenticationRequest;
import com.scs.identity.exception.AppException;
import com.scs.identity.exception.ErrorCode;
import com.scs.identity.repository.UserRepository;
import com.scs.identity.util.ErrorMessageUtilHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage(ErrorMessageUtilHolder.getErrorMessageUtil().getMessage("USER_NOT_EXISTED"));

            return new AppException(errorCode);
        });


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
