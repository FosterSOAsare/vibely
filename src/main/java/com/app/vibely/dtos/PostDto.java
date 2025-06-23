package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Integer id;

    @JsonProperty("owner_email")
    private String ownerEmail;

    private String username;

    @JsonProperty("isFollowing")
    private Boolean isFollowing;

    @JsonProperty("profile_picture")
    private String profilePicture;

    private String caption;

    private Integer comments;

    private Integer ownerId;

    private Integer likes;

    @JsonProperty("isLiked")
    private Boolean isLiked;

    private String imageUrl;

    @JsonProperty("createdAt")
    private Instant createdAt;

    @JsonProperty("isSaved")
    private Boolean isSaved;
}