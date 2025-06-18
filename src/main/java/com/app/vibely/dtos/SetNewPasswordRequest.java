package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetNewPasswordRequest {
    @NotBlank(message = "Email/username is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 25, message = "Password must be between 8 to 25 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,25}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String password;

    @NotBlank(message = "Verification code is required")
    @Size(min = 6, max = 6, message = "Verification code must be 6 characters long")
    private String code;
}
