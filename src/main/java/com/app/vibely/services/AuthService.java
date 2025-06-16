package com.app.vibely.services;

import com.app.vibely.dtos.*;
import com.app.vibely.entities.User;
import com.app.vibely.exceptions.DuplicateUserException;
import com.app.vibely.mappers.UserMapper;
import com.app.vibely.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Service responsible for authentication-related operations such as login,
 * refreshing access tokens, and retrieving the current authenticated user.
 */
@AllArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves the currently authenticated user based on the JWT principal.
     *
     * @return the authenticated {@link User}, or null if not found
     */
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var principal =  authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return null;
        }

        return userRepository.findById((Integer) principal).orElse(null);
    }

    /**
     * Authenticates a user using the provided credentials and generates access and refresh tokens.
     *
     * @param request the login request containing email and password
     * @return a {@link LoginResponse} containing access and refresh JWTs
     */
    public LoginResponse login(LoginRequest request) {
        // Authenticate the credentials using Spring Security
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword()));

        // If authentication succeeds, fetch the user and generate tokens
        User user = userRepository.findByUsernameOrEmail(request.getIdentifier()).orElseThrow(() -> new RuntimeException("User not found after authentication"));
        System.out.println("user" + user);
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param refreshToken the refresh token to validate and parse
     * @return a new access {@link Jwt} if valid
     * @throws BadCredentialsException if the token is invalid or expired
     */
    public Jwt refreshAccessToken(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        var user = userRepository.findById(jwt.getUserId()).orElseThrow();
        return jwtService.generateAccessToken(user);
    }



    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("The provide email already exists");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("The provided username already exists");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public void changeUserPassword(Integer user_id , ChangeUserPasswordRequest request){
        //  Get user
        User user = userRepository.findById(user_id).orElseThrow();

        //  Compare user password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        //  Change password
        String newEncodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newEncodedPassword);

        //  Save user
        userRepository.save(user);
    }


}
