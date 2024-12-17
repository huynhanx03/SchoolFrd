package com.scs.collegeexamscores.service;

import com.scs.collegeexamscores.dto.response.BucketAggregationResponse;
import com.scs.collegeexamscores.dto.response.ScoreResponse;
import com.scs.collegeexamscores.entity.BucketAggregation;
import com.scs.collegeexamscores.entity.Score;
import com.scs.collegeexamscores.exception.AppException;
import com.scs.collegeexamscores.exception.ErrorCode;
import com.scs.collegeexamscores.mapper.BucketAggregationMapper;
import com.scs.collegeexamscores.mapper.ScoreMapper;
import com.scs.collegeexamscores.repository.ScoreCustomRepository;
import com.scs.collegeexamscores.repository.ScoreRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScoreService {
    ScoreRepository scoreRepository;
    ScoreCustomRepository scoreCustomRepository;
    ScoreMapper scoreMapper;
    BucketAggregationMapper bucketAggregationMapper;

    public ScoreResponse getScoreByStudentIdAndYear(String studentId, int year) {
        var score = scoreRepository.findByStudentIdAndYear(studentId, year).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            return new AppException(errorCode);
        });;

        return scoreMapper.toScoreResponse(score);
    }

    public List<BucketAggregationResponse> getScoreDistribution(String subject, int year) {
        List<BucketAggregation> results = scoreCustomRepository.getScoreDistribution(subject, year);

        return results.stream()
                .map(bucketAggregationMapper::toBucketAggregationResponse)
                .collect(Collectors.toList());
    }

}
