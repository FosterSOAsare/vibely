package com.app.vibely.mappers;

import com.app.vibely.dtos.PostBookmarksDto;
import com.app.vibely.entities.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostBookmarksMapper {
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "username", source = "user.username")
    PostBookmarksDto toDto(Bookmark bookmark);
}
