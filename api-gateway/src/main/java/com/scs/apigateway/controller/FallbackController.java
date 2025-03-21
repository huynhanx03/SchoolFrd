package com.scs.apigateway.controller;

import com.scs.apigateway.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<ApiResponse<Object>> fallback() {
        ApiResponse<Object> response = ApiResponse.builder()
                .code(503)
                .message("Service is temporarily unavailable. Please try again later.")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}

