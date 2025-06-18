package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStoryDto {

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @Size(max = 255, message = "Caption must not exceed 255 characters")
    private String caption;
}
