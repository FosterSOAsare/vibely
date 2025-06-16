package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditBioRequest {
    @NotBlank(message = "Bio is required")
    @Size(max = 255, message = "Bio must be less than 255 characters")
    @Size(min =10 , message = "Name must be more than 10 characters")
    private String bio;
}

