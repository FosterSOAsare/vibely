package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class PostLikesDto {
    private Integer id;
    private Integer postId;
    private String profilePicture;
    private String username;
    private String ownerEmail;
    private Integer userId;
    private Instant createdAt;
}
