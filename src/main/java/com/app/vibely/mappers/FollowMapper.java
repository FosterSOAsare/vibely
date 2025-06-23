package com.app.vibely.mappers;

import com.app.vibely.dtos.FollowDto;
import com.app.vibely.entities.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(target="id" , source="follower.id" )
    @Mapping(target="follow_id" , source="id" )
    @Mapping(target="profilePicture" , source="follower.profilePicture" )
    @Mapping(target="username" , source="follower.username")
    @Mapping(target="isFollowing" , expression="java(true)")
    FollowDto toDto(Follow follow);
}
