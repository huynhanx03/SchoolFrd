package com.scs.collegeexamscores.repository;

import com.scs.collegeexamscores.entity.BucketAggregation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScoreCustomRepository {
    MongoTemplate mongoTemplate;

    public List<BucketAggregation> getScoreDistribution(String subject, int year) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("year").is(year).and(subject).gte(0)),
                Aggregation.bucket(subject)
                        .withBoundaries(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                        .withDefaultBucket("10")
                        .andOutputCount().as("count")
        );

        AggregationResults<BucketAggregation> results = mongoTemplate.aggregate(
                aggregation, "score", BucketAggregation.class
        );

        return results.getMappedResults();
    }
}
