package com.scentify.backend.controller;

import com.scentify.backend.model.User;
import com.scentify.backend.service.AuthenticationService;
import com.scentify.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Misal set default role jika user tidak menyertakan
        if (user.getRole() == null) {
            user.setRole("BUYER");
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userService.login(loginRequest.email, loginRequest.password);
        if (userOpt.isPresent()) {
            String token = authenticationService.login(loginRequest.email, loginRequest.password);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(401).body("Login Gagal: Email atau password salah");
        }
    }

    // DTO class untuk login request
    public static class LoginRequest {
        public String email;
        public String password;
    }

    // DTO class untuk balikan token JWT
    public static class JwtResponse {
        public String token;

        public JwtResponse(String token) {
            this.token = token;
        }
    }
}
