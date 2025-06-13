package com.app.vibely.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;


@Getter
@AllArgsConstructor
public class UserDto {
    private String username;
    private Integer id;
    private String bio;
    private String country;
    private String profile_picture;
    private String name;
    private String email;
    private String gender;
    private Instant createdAt;

//    Add other fields here
    // isFollowed ,  notifications , followers , followings , saved_posts
}
