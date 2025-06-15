package com.app.vibely.mappers;

import com.app.vibely.dtos.AuthDto;
import com.app.vibely.dtos.RegisterUserRequest;
import com.app.vibely.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
  AuthDto toDto(User user);
  User toEntity(RegisterUserRequest request);
}
