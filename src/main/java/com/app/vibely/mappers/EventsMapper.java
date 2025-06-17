package com.app.vibely.mappers;

import com.app.vibely.dtos.EventsDto;
import com.app.vibely.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsMapper {
    @Mapping(target = "comments", expression = "java(event.calculateComments())")
    @Mapping(target = "likes", expression = "java(event.calculateLikes())")
    @Mapping(target = "coordinates", expression = "java(event.createCoordinates())")
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "ownerEmail", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "images", expression = "java(event.createEventImages())")
    EventsDto toDto(Event event);
}
