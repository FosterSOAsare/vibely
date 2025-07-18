package com.app.vibely.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email/username is required")
    private String identifier;

    @NotBlank(message = "Password is required" )
    @Size(min = 8 , message = "Password must be at least 8 characters long")
    private String password;
}
