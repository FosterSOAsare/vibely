package com.app.vibely.controllers;

import com.app.vibely.dtos.UserDto;
import com.app.vibely.entities.User;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.UserRepository;
import com.app.vibely.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @GetMapping("")
    public Iterable<UserDto> getAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(userMapper::toDto).toList();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> me() {
        var user = authService.getCurrentUser();

        System.out.println("User " + user);

        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "Get a user with their id")
    public ResponseEntity<UserDto> getAUser(@PathVariable Integer user_id){
        User user=  userRepository.findById(user_id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(userMapper.toDto(user));
        }
    }



//    Next steps: edit bio , username , name , gender , profile picture
//
}
