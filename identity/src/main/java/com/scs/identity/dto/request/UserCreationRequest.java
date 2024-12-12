package com.scs.identity.dto.request;

import com.scs.identity.validator.ValidPassword;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
}
