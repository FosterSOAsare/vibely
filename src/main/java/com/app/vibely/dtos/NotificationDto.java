package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class NotificationDto {
    private Integer id;
    private String message;
    
    @JsonProperty("isRead")
    private Boolean isRead;
    
    @JsonProperty("createdAt")
    private Instant createdAt;
    
    private String link;

    // User who received the notification
    private Integer userId;
    private String username;
    
    @JsonProperty("profile_picture")
    private String profilePicture;
}