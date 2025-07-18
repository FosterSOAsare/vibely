package com.app.vibely.mappers;

import com.app.vibely.dtos.RegisterUserRequest;
import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "followings" , expression = "java(user.calculateFollowings())")
    @Mapping(target = "followers" , expression = "java(user.calculateFollowers())")
    @Mapping(target = "isFollowing", ignore = true)
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);
}
