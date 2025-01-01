package com.scs.school.service;

import com.scs.school.dto.request.SchoolRequest;
import com.scs.school.dto.response.SchoolResponse;
import com.scs.school.entity.School;
import com.scs.school.mapper.SchoolMapper;
import com.scs.school.repository.SchoolRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolService {
    SchoolRepository schoolRepository;
    SchoolMapper schoolMapper;

    public SchoolResponse createPost(SchoolRequest request){
        School school = School.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();

        school = schoolRepository.save(school);
        return schoolMapper.toSchoolResponse(school);
    }

    public List<SchoolResponse> getSchools() {
        List<School> results = schoolRepository.findAll();

        return results.stream()
                .map(schoolMapper::toSchoolResponse)
                .collect(Collectors.toList());
    }

    public SchoolResponse getSchool(String id) {
        return schoolMapper.toSchoolResponse(schoolRepository.findById(id).orElseThrow(
                () -> new RuntimeException("School not found")
        ));
    }
}
