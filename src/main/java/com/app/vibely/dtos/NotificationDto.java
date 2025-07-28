package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class NotificationDto {
    private Integer id;
    private String message;
    private Boolean isRead;
    private Instant createdAt;
    private String link;

    // User who received the notification
    private Integer userId;
    private String username;
    private String profilePicture;
}