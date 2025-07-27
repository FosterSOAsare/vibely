package com.app.vibely.mappers;

import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.EventBookmarks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserEventBookmarksMapper {
    @Mapping(target = "profilePicture", source = "event.user.profilePicture")
    @Mapping(target = "ownerId", source = "event.user.id")
    @Mapping(target = "username", source = "event.user.username")
    @Mapping(target = "id", expression = "java(bookmark.getEvent().getId().toString())")
    @Mapping(target = "name", source = "event.name")
    @Mapping(target = "description", source = "event.description")
    @Mapping(target = "createdAt", source = "event.createdAt")
    @Mapping(target = "location", source="event.location")
    @Mapping(target = "coordinates", expression = "java(bookmark.getEvent().createCoordinates())")
    @Mapping(target = "eventTime", source = "event.eventTime")

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "isLiked", ignore = true)
    @Mapping(target = "isSaved", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "isFollowing", ignore = true)
    EventsDto toDto(EventBookmarks bookmark);
}
