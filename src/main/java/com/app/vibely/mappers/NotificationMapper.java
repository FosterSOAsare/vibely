package com.app.vibely.mappers;

import com.app.vibely.dtos.NotificationDto;
import com.app.vibely.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.profilePicture", target = "profilePicture")
    NotificationDto toDto(Notification notification);
}