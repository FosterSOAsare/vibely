package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreatePostComment {
    @NotBlank(message = "Text is required")
    @Size(max = 255, message = "Text must not exceed 255 characters")
    private String text;
}
