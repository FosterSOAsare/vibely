package com.app.vibely.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EventsBookmarksDto {
    private Integer id;
    private Instant bookmarkedAt;

    private Integer eventId;
    private Integer userId;
    private String username;
    private String profilePicture;
}
