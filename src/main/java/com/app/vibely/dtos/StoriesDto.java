package com.app.vibely.dtos;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@Data
public class StoriesDto {
    private Integer id;
    private String imageUrl;
    private String caption;
    private Instant createdAt;

    private Integer userId;
    private String username;
    @JsonProperty("profile_picture")
    private String profilePicture;

    //    Determines whether current user has viewed this story
    private boolean viewed;
}
