package com.scs.school.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(value = "school")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class School {
    @MongoId
    String id;
    String code;
    String name;
    String description;
}
