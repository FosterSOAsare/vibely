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
    private String name;
    
    private String username;
    
    @JsonProperty("profile_picture")
    private String profilePicture;
    
    private List<String> images;
    
    private String description;
    
    @JsonProperty("isLiked")
    private Boolean isLiked;
    
    private String location;
    
    private List<Double> coordinates;
    
    private Integer comments;
    
    private String id;
    
    @JsonProperty("isSaved")
    private Boolean isSaved;
    
    private Integer likes;
    
    @JsonProperty("owner_id")
    private Integer ownerId;
    
    @JsonProperty("isFollowing")
    private Boolean isFollowing;
    
    @JsonProperty("createdAt")
    private Instant createdAt;
    
    @JsonProperty("eventTime")
    private Instant eventTime;
}