package com.scs.identity.dto.request;

import com.scs.identity.util.ErrorMessageUtilHolder;
import jakarta.validation.constraints.Size;

public class UserCreationRequest {

    @Size(min = 8, max = 50, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 8, max = 50, message = "PASSWORD_INVALID")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
