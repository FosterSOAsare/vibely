package com.app.vibely.dtos;

import com.app.vibely.entities.Follow;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDto {
    private String username;
    private Integer id;
    private String bio;
    private String country;
    @JsonProperty("profile_picture")
    private String profilePicture;
    private String name;
    private String email;
    private String gender;
    private int followings;
    private int followers;
    private boolean isFollowing;

//    Add other fields here
    // isFollowed ,  notifications
}
