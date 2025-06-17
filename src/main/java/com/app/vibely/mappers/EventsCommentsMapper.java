package com.app.vibely.mappers;

import com.app.vibely.dtos.EventCommentsDto;
import com.app.vibely.entities.EventComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventsCommentsMapper {
    @Mapping(target = "profilePicture", source = "user.profilePicture")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "username", source = "user.username")

    @Mapping(target = "likesCount", expression = "java(comment.getLikesCount())")
    EventCommentsDto toDto(EventComment comment);
}
