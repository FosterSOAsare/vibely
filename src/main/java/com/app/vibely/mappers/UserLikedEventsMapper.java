package com.app.vibely.mappers;

import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.EventLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserLikedEventsMapper {
    @Mapping(target = "profilePicture", source = "event.user.profilePicture")
    @Mapping(target = "ownerEmail", source = "event.user.email")
    @Mapping(target = "username", source = "event.user.username")
    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "description", source = "event.description")
    @Mapping(target = "createdAt", source = "event.createdAt")
    @Mapping(target = "location", source="event.location")

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "isLiked", ignore = true)
    @Mapping(target = "isSaved", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "isFollowing", ignore = true)
    EventsDto toDto(EventLike like);
}
