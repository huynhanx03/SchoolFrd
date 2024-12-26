package com.scs.identity.repository.httpclient;

import com.scs.identity.configuration.AuthenticationRequestInterceptor;
import com.scs.identity.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scs.identity.dto.request.ProfileCreationRequest;
import com.scs.identity.dto.response.UserProfileResponse;

@FeignClient(name = "profile", url = "${app.services.profile}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @PostMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}