package com.app.vibely.mappers;

import com.app.vibely.dtos.PostLikesDto;
import com.app.vibely.entities.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostLikesMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    PostLikesDto toDto(Like like);
}
