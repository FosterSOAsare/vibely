package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.Data;

@Data
public class CreatePostRequest {
    @Size(max = 255, message = "Caption must not exceed 255 characters")
    private String caption;

    @NotBlank(message = "Image URL is required")
    @URL(message = "Invalid image URL format")
    private String imageUrl;
}
