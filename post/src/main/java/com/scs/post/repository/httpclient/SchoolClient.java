package com.scs.post.repository.httpclient;

import com.scs.post.configuration.AuthenticationRequestInterceptor;
import com.scs.post.dto.response.ApiResponse;
import com.scs.post.dto.response.SchoolResponse;
import com.scs.post.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "school", url = "${app.services.school.url}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface SchoolClient {
    @GetMapping("/schools/{schoolId}")
    ApiResponse<SchoolResponse> getSchool(@PathVariable String schoolId);
}
