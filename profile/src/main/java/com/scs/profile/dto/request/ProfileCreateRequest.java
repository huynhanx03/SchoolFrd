package com.scs.profile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreateRequest {
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;
}
