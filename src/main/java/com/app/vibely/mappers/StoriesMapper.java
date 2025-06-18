package com.app.vibely.mappers;

import com.app.vibely.dtos.StoriesDto;
import com.app.vibely.entities.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoriesMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.profilePicture", target = "profilePicture")
    StoriesDto toDto(Story story);
}
