package com.app.vibely.controllers;

import com.app.vibely.common.PagedResponse;
import com.app.vibely.dtos.*;
import com.app.vibely.entities.User;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.UserRepository;
import com.app.vibely.services.AuthService;
import com.app.vibely.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Users")
@SuppressWarnings("unused")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<PagedResponse<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size , Principal principal) {
        Integer currentUserId = Integer.parseInt(principal.getName());
        PagedResponse<UserDto> userDtos = userService.getUsers(page, size , currentUserId );
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> me() {
        var user = authService.getCurrentUser();

        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);

    }

    @PutMapping("/profile/name")
    public ResponseEntity<?> editName(@Valid @RequestBody EditNameRequest request, UriComponentsBuilder uriBuilder){
        // Get name and logged-in user
        var user = authService.getCurrentUser();
        User updatedUser = userService.editName(request , user.getId());
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PutMapping("/profile/gender")
    public ResponseEntity<?> editGender(@Valid @RequestBody EditGenderRequest request, UriComponentsBuilder uriBuilder){
        // Get name and logged-in user
        var user = authService.getCurrentUser();

//        Check gender value
        User updatedUser = userService.editGender(request , user.getId());
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PutMapping("/profile/bio")
    public ResponseEntity<?> editBio(@Valid @RequestBody EditBioRequest request, UriComponentsBuilder uriBuilder){
        // Get name and logged-in user
        var user = authService.getCurrentUser();

//        Check gender value
        User updatedUser = userService.editBio(request , user.getId());
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @PutMapping("/profile/profile_picture")
    public ResponseEntity<?> editProfilePicture(@Valid @RequestBody EditProfilePictureRequest request, UriComponentsBuilder uriBuilder){
        // Get name and logged-in user
        var user = authService.getCurrentUser();

//        Check gender value
        User updatedUser = userService.editProfilePicture(request , user.getId());
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "Get a user with their id")
    public ResponseEntity<UserDto> getAUser(@PathVariable Integer user_id , Principal principal){
        Integer currentUserId = Integer.parseInt(principal.getName());
        UserDto userDto = userService.getUser(user_id , currentUserId);
        if(userDto == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(userDto);
        }
    }
}
