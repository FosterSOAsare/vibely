package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EventCommentsDto {
    private Integer id;
    private String text;
    private Instant createdAt;

    private Integer eventId;
    private Integer userId;
    private String username;

    @JsonProperty("profile_picture")
    private String profilePicture;

    private int likesCount;
}
