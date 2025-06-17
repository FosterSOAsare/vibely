package com.app.vibely.mappers;

import com.app.vibely.dtos.EventsLikesDto;
import com.app.vibely.entities.EventLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsLikesMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "ownerEmail", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    EventsLikesDto toDto(EventLike like);
}
