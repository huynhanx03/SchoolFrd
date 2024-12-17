package com.scs.collegeexamscores.mapper;

import com.scs.collegeexamscores.dto.response.BucketAggregationResponse;
import com.scs.collegeexamscores.entity.BucketAggregation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BucketAggregationMapper {
    BucketAggregationResponse toBucketAggregationResponse(BucketAggregation bucketAggregation);
}
