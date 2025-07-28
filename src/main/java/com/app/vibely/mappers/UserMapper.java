package com.app.vibely.mappers;

import com.app.vibely.dtos.RegisterUserRequest;
import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserMapper {
    
    @Mapping(target = "followings" , expression = "java(user.calculateFollowings())")
    @Mapping(target = "followers" , expression = "java(user.calculateFollowers())")
    @Mapping(target = "notifications" , expression = "java(user.calculateUnreadNotifications())")
    @Mapping(target = "posts" , expression = "java(user.calculatePosts())")
    @Mapping(target = "events" , expression = "java(user.calculateEvents())")
    @Mapping(target = "isFollowing", ignore = true)
    public abstract UserDto toDto(User user);

    public abstract User toEntity(RegisterUserRequest request);
}
