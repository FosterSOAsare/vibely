package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class CreateCommentRequest {
    @NotBlank(message = "Text is required")
    @Size(max = 255, message = "Text must not exceed 255 characters")
    @Size(min = 1, message = "Text must be at least a character long")
    private String text;
}
