package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventsDto {
    private Integer id;

    @JsonProperty("owner_email")
    private String ownerEmail;

    private String username;

    @JsonProperty("isFollowing")
    private Boolean isFollowing;

    @JsonProperty("profile_picture")
    private String profilePicture;

    private String description;

    private Integer comments;

    private Integer likes;

    @JsonProperty("isLiked")
    private Boolean isLiked;

    private List<String> images;

    @JsonProperty("createdAt")
    private Instant createdAt;

    private List<Double> coordinates;

    private String location;

    @JsonProperty("isSaved")
    private Boolean isSaved;
}