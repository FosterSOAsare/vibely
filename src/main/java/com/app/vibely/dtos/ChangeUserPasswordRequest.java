package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeUserPasswordRequest {
    @NotBlank(message = "Current password is required")
    @Size(min = 8, max = 25, message = "Current password must be between 8 to 25 characters long.")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 25, message = "New password must be between 8 to 25 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,25}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character.")
    private String newPassword;
}
