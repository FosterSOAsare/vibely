package com.app.vibely.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class FollowDto {
   private Integer follow_id;
   private Integer id;
   private String username;
   @JsonProperty("profile_picture")
   private String profilePicture;
   private Instant createdAt;
   private Boolean isFollowing;

   private Integer followersCount;
}
