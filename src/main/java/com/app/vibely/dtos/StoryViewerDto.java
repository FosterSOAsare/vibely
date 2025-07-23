package com.app.vibely.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryViewerDto {
    private Integer id;
    private String username;
    @JsonProperty("profile_picture")
    private String profilePicture;
    private Instant viewedAt;
}
