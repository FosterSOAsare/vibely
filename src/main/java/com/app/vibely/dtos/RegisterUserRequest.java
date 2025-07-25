package com.app.vibely.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain  letters, numbers, underscores, and dots")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 25, message = "Password must be between 8 to 25 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,25}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String password;
}
