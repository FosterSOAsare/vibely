package com.app.vibely.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class CreateEventRequest {

    @NotBlank(message = "Event name is required")
    @Size(max = 100, message = "Event name must not exceed 100 characters")
    private String name;

    @NotEmpty(message = "At least one image URL is required")
    private List<@NotBlank(message = "Image URL cannot be blank") String> images;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Coordinates are required")
    @Size(min = 2, max = 2, message = "Coordinates must contain exactly 2 values: latitude and longitude")
    private List<@NotNull(message = "Coordinate values cannot be null") Double> coordinates;

    private Instant eventTime;

    @PositiveOrZero(message = "Price must be non-negative")
    private BigDecimal price;
}
