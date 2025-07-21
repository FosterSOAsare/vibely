package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserWithStoryDto {
    private String username;
    private Integer id;
    @JsonProperty("profile_picture")
    private String profilePicture;
    private boolean allViewed;
}
