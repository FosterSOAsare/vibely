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
    private Integer followings;
    private Integer followers;
    private Boolean isFollowing;

//    Add other fields here
//    notifications
}
