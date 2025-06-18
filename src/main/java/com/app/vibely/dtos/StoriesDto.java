package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class StoriesDto {
    private Integer id;
    private String imageUrl;
    private String caption;
    private Instant createdAt;

    private Integer userId;
    private String username;
    private String profilePicture;
}
