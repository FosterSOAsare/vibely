package com.app.vibely.mappers;

import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
