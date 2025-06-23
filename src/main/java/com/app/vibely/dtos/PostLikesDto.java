package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class PostLikesDto {
    private Integer id;
    private Integer postId;
    @JsonProperty("profile_picture")
    private String profilePicture;
    private String username;
    private String email;
    private Integer userId;
    private Instant createdAt;
}
