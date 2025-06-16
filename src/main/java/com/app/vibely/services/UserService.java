package com.app.vibely.services;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.*;
import com.app.vibely.entities.Comment;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.ResourceNotFoundException;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    public PagedResponse<UserDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        // Convert user to userdto
        List<UserDto> dtos = usersPage.getContent().stream()
                .map(userMapper::toDto)
                .toList();


        return new PagedResponse<UserDto>(dtos, page, size,
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.hasNext(),
                usersPage.hasPrevious());
    }
}
