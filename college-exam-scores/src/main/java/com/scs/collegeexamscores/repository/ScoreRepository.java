package com.scs.collegeexamscores.repository;

import com.scs.collegeexamscores.entity.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScoreRepository extends MongoRepository<Score, String> {
    Optional<Score> findByStudentIdAndYear(String studentId, int year);
}
