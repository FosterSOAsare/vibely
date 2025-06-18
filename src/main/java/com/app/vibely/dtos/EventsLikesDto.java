package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class EventsLikesDto {
    private Integer id;
    private Integer eventId;
    private String profilePicture;
    private String username;
    private String ownerEmail;
    private Integer userId;
    private Instant createdAt;
}
