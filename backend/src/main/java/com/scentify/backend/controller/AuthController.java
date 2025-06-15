package com.scentify.backend.controller;

import com.scentify.backend.model.User;
import com.scentify.backend.repository.UserRepository;
import com.scentify.backend.service.AuthenticationService;
import com.scentify.backend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationService authenticationService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authenticationService.login(loginRequest.email, loginRequest.password);

            User user = userRepository.findByEmail(loginRequest.email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("username", user.getNama()); // atau user.getUsername() jika ada
            response.put("userId", user.getId());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // DTO class untuk menerima JSON login
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authenticationService.register(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DTO untuk data register
    public static class RegisterRequest {
        public String nama;
        public String email;
        public String password;
        public String alamat;
        public String role;
    }
}
