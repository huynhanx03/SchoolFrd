package com.scs.school.mapper;

import com.scs.school.dto.response.SchoolResponse;
import com.scs.school.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolResponse toSchoolResponse(School school);
}
