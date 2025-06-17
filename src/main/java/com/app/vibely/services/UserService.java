package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.*;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.FollowRepository;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FollowRepository followRepository;

    public <T> User editProfile(Integer user_id , T request , BiConsumer<User, T> updater){
        // Find user by id and set name
        var user = userRepository.findById(user_id).orElseThrow();
        updater.accept(user, request);
        userRepository.save(user);
        return user;
    }
    public User editName(EditNameRequest request , Integer user_id) {
        return editProfile(user_id, request, (user, req) -> user.setName(req.getName()));
    }


    public User editGender(EditGenderRequest request , Integer user_id) {
        return editProfile(user_id, request, (user, req) -> user.setGender(req.getGender()));
    }


    public User editBio(EditBioRequest request , Integer user_id) {
        return editProfile(user_id, request, (user, req) -> user.setBio(req.getBio()));
    }

    public User editProfilePicture(EditProfilePictureRequest request , Integer user_id) {
        return editProfile(user_id, request, (user, req) -> user.setProfilePicture(req.getProfilePicture()));
    }

    // ✅ Get users with pagination
    public PagedResponse<UserDto> getUsers(int page, int size , Integer currentUserid) {
        Pageable pageable = PageRequest.of(page, size , Sort.by("id").ascending());
        Page<User> usersPage = userRepository.findAll(pageable);

        // Convert user to userDto
        List<UserDto> dtos = usersPage.getContent().stream()
                .map((user) -> {
                    UserDto userDto = userMapper.toDto(user);
                    userDto.setIsFollowing(followRepository.checkIfUserIsFollowed(user.getId() , currentUserid));
                    return userDto;
                })
                .toList();

        return new PagedResponse<>(dtos, page, size, usersPage.getTotalElements(), usersPage.getTotalPages(), usersPage.hasNext(), usersPage.hasPrevious());
    }

    public UserDto getUser(Integer userId , Integer currentUserid) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("The provided user id doesn't exist."));
        // Convert user to userDto
        UserDto userDto= userMapper.toDto(user);
        userDto.setIsFollowing(followRepository.checkIfUserIsFollowed(user.getId() , currentUserid));
        return userDto;
    }
}
