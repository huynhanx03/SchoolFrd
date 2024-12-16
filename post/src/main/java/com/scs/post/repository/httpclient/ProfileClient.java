package com.scs.post.repository.httpclient;

import com.scs.post.configuration.AuthenticationRequestInterceptor;
import com.scs.post.dto.response.ApiResponse;
import com.scs.post.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile", url = "${app.services.profile.url}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @GetMapping("/internal/users/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId);
}
