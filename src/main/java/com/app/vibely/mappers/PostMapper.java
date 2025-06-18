package com.app.vibely.mappers;

import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "comments", expression = "java(post.calculateComments())")
    @Mapping(target = "likes", expression = "java(post.calculateLikes())")
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "ownerEmail", source = "user.email")
    @Mapping(target = "username", source = "user.username")

    @Mapping(target = "isLiked", ignore = true)
    @Mapping(target = "isSaved", ignore = true)
    @Mapping(target = "isFollowing", ignore = true)
    PostDto toDto(Post post);
}
