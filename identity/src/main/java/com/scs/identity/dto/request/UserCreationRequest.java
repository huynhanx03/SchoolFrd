package com.scs.identity.dto.request;

import jakarta.validation.constraints.Size;

import com.scs.identity.validator.ValidPassword;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 8, max = 50, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 50, message = "PASSWORD_INVALID")
    @ValidPassword(message = "BAD_PASSWORD")
    String password;

    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate dob;
}
