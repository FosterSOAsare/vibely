package com.app.vibely.mappers;

import com.app.vibely.dtos.RegisterUserRequest;
import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import com.app.vibely.repositories.EventRepository;
import com.app.vibely.repositories.PostRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserMapper {
    
    @Autowired
    protected PostRepository postRepository;
    
    @Autowired
    protected EventRepository eventRepository;
    
    @Mapping(target = "followings" , expression = "java(user.calculateFollowings())")
    @Mapping(target = "followers" , expression = "java(user.calculateFollowers())")
    @Mapping(target = "posts" , expression = "java((int) postRepository.countByUserId(user.getId()))")
    @Mapping(target = "events" , expression = "java((int) eventRepository.countByUserId(user.getId()))")
    @Mapping(target = "isFollowing", ignore = true)
    public abstract UserDto toDto(User user);

    public abstract User toEntity(RegisterUserRequest request);
}
