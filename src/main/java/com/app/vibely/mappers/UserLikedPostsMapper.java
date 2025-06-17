package com.app.vibely.mappers;

import com.app.vibely.dtos.PostDto;
import com.app.vibely.entities.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserLikedPostsMapper {
    @Mapping(target = "profilePicture", source = "post.user.profilePicture")
    @Mapping(target = "ownerEmail", source = "post.user.email")
    @Mapping(target = "username", source = "post.user.username")
    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "imageUrl", source = "post.imageUrl")
    @Mapping(target = "caption", source = "post.caption")
    @Mapping(target = "createdAt", source = "post.createdAt")
    PostDto toDto(Like like );
}
