package com.scs.collegeexamscores.controller;

import com.scs.collegeexamscores.dto.response.ApiResponse;
import com.scs.collegeexamscores.dto.response.BucketAggregationResponse;
import com.scs.collegeexamscores.dto.response.ScoreResponse;
import com.scs.collegeexamscores.service.ScoreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/scores")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScoreController {
    ScoreService scoreService;

    @GetMapping("/search")
    ApiResponse<ScoreResponse> searchScore(
            @RequestParam(value = "student-id", required = false, defaultValue = "1000001") String studentId,
            @RequestParam(value = "year", required = false, defaultValue = "2024") int size
            ){
        return ApiResponse.<ScoreResponse>builder()
                .data(scoreService.getScoreByStudentIdAndYear(studentId, size))
                .build();
    }

    @GetMapping("/data-chart")
    public ApiResponse<List<BucketAggregationResponse>> getScoreChart(
            @RequestParam("subject") String subject,
            @RequestParam(value = "year", required = false, defaultValue = "2024") int year) {
        return ApiResponse.<List<BucketAggregationResponse>>builder()
                .data(scoreService.getScoreDistribution(subject, year))
                .build();
    }
}