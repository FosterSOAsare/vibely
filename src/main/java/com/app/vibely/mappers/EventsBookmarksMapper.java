package com.app.vibely.mappers;

import com.app.vibely.dtos.EventsBookmarksDto;
import com.app.vibely.entities.EventBookmarks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsBookmarksMapper {
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "username", source = "user.username")
    EventsBookmarksDto toDto(EventBookmarks bookmark);
}
