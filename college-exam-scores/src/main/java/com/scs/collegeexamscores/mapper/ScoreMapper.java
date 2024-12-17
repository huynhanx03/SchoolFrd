package com.scs.collegeexamscores.mapper;

import com.scs.collegeexamscores.dto.response.ScoreResponse;
import com.scs.collegeexamscores.entity.Score;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    ScoreResponse toScoreResponse(Score score);
}
