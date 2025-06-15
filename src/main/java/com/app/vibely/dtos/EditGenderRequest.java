package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class EditGenderRequest {
    @NotBlank(message = "Gender is required")
    @Size(max = 255, message = "Gender must be less than 255 characters")
    @Size(min = 4 , message = "Gender must be more than 4 characters")
    private String gender;
}
