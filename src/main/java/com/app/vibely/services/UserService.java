package com.app.vibely.services;

import com.app.vibely.dtos.EditBioRequest;
import com.app.vibely.dtos.EditGenderRequest;
import com.app.vibely.dtos.EditNameRequest;
import com.app.vibely.dtos.EditProfilePictureRequest;
import com.app.vibely.entities.User;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
