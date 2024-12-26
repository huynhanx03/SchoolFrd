package com.scs.school.controller;

import com.scs.school.dto.request.SchoolRequest;
import com.scs.school.dto.response.ApiResponse;
import com.scs.school.dto.response.SchoolResponse;
import com.scs.school.service.SchoolService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/schools")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolController {
    SchoolService schoolService;

    @PostMapping
    ApiResponse<SchoolResponse> createPost(@RequestBody SchoolRequest request){
        return ApiResponse.<SchoolResponse>builder()
                .data(schoolService.createPost(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<SchoolResponse>> getUsers() {
        return ApiResponse.<List<SchoolResponse>>builder().data(schoolService.getSchools()).build();
    }
}