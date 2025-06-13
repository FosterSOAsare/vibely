package com.app.vibely.controllers;

import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("")
    public Iterable<UserDto> getAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getUsername(), user.getId(), user.getCountry() , user.getBio(), user.getProfilePicture(), user.getName(), user.getEmail(), user.getGender(), user.getCreatedAt())).toList();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getAUser(@PathVariable Integer user_id){
        User user=  userRepository.findById(user_id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }else{
            UserDto userDto =  new UserDto(user.getUsername(), user.getId(), user.getCountry() , user.getBio(), user.getProfilePicture(), user.getName(), user.getEmail(), user.getGender(), user.getCreatedAt());
            return ResponseEntity.ok(userDto);
        }
    }
}
