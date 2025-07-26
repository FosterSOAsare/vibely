package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class NotificationDto {
    private Integer id;
    private String message;
    private Boolean isRead;
    private Instant createdAt;
    private String type;
    private Integer relatedEntityId;
    private String relatedEntityType;

    // User who received the notification
    private Integer userId;
    private String username;
    private String profilePicture;

    // User who triggered the notification
    private Integer triggeredByUserId;
    private String triggeredByUsername;
    private String triggeredByProfilePicture;
}