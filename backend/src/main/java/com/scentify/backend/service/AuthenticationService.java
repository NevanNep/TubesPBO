package com.scentify.backend.service;

import com.scentify.backend.model.User;
import com.scentify.backend.repository.UserRepository;
import com.scentify.backend.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getPassword().equals(password)) {
            return jwtUtil.generateToken(user.getEmail(), user.getId().toString());
        }
        throw new RuntimeException("Invalid credentials");
    }
}