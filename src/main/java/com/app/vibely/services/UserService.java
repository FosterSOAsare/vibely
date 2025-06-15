package com.app.vibely.services;

import com.app.vibely.entities.User;
import com.app.vibely.repositories.UserRepository;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
public class UserService {
    private final UserRepository userRepository;


}
