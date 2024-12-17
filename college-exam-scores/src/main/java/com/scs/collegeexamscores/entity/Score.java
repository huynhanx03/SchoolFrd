package com.scs.collegeexamscores.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(value = "score")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Score {
    @MongoId
    String id;
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
