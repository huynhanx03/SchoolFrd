package com.scs.identity.dto.request;

import jakarta.validation.constraints.Size;

public class UserCreationRequest {

    @Size(min = 8, max = 50, message = "Username must be at least 8 characters and no more than 50 characters long")
    private String username;

    @Size(min = 8, max = 50, message = "Password must be at least 8 characters")
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
