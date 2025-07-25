package com.app.vibely.controllers;

import com.app.vibely.config.JwtConfig;
import com.app.vibely.dtos.*;
import com.app.vibely.entities.User;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@SuppressWarnings("unused")
public class AuthController {
    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {

        UserDto userDto = authService.registerUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {

        var loginResult = authService.login(request);

        var refreshToken = loginResult.getRefreshToken().toString();
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(loginResult.getAccessToken().toString());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        var accessToken = authService.refreshAccessToken(refreshToken);
        return new JwtResponse(accessToken.toString());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        var user = authService.getCurrentUser();
        if (user == null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized", "message", "Token expired or malformed"));

        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("change-password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangeUserPasswordRequest request, UriComponentsBuilder uriBuilder) {
        User user = authService.getCurrentUser();
        authService.changeUserPassword(user.getId() , request) ;
        UserDto userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("verify-account")
    public ResponseEntity<?> verifyUserAccount(@Valid @RequestBody VerifyAccountRequest request, UriComponentsBuilder uriBuilder) {
        User user = authService.verifyUserAccount(request) ;
        UserDto userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("request-password-reset")
    public ResponseEntity<?> passwordResetRequest(@Valid @RequestBody PasswordResetRequest request, UriComponentsBuilder uriBuilder) {
        UserDto userDto = authService.requestResetPassword(request.getIdentifier());
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("set-password")
    public ResponseEntity<?>setNewPassword(@Valid @RequestBody SetNewPasswordRequest request, UriComponentsBuilder uriBuilder) {
        UserDto userDto = authService.setNewPassword(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.ok(userDto);
    }



}
