package com.scs.identity.controller;

import com.scs.identity.dto.request.ApiResponse;
import com.scs.identity.dto.request.AuthenticationRequest;
import com.scs.identity.dto.request.IntrospectRequest;
import com.scs.identity.dto.response.AuthenticationResponse;
import com.scs.identity.dto.response.IntrospectResponse;
import com.scs.identity.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var data = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .data(data)
                .build();
    }
}
