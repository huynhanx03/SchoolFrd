package com.scs.collegeexamscores.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreResponse {
    String studentId;
    double math;
    double literature;
    double language;
    double physics;
    double chemistry;
    double biology;
    double history;
    double geography;
    double education;
    String languageId;
    int year;
}
