package com.app.vibely.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class FollowDto {
   private Integer follow_id;
   private Integer user_id;
   private String username;
   private String profilePicture;
   private Instant createdAt;
   private Boolean isFollowing;

   private Integer followersCount;
}
