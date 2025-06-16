package com.app.vibely.mappers;

import com.app.vibely.dtos.PostCommentsDto;
import com.app.vibely.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostCommentsMapper {
        @Mapping(target = "profilePicture", source = "user.profilePicture")
        @Mapping(target = "userId", source = "user.id")
        @Mapping(target = "postId", source = "post.id")
        @Mapping(target = "username", source = "user.username")

        @Mapping(target = "likesCount", expression = "java(comment.getLikesCount())")
        PostCommentsDto toDto(Comment comment);
}
