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
public class PostCommentsDto {
    private Integer id;
    private String text;
    private Instant createdAt;

    private Integer postId;
    private Integer userId;
    private String username;
    private String profilePicture;

    private int likesCount;
}
