package com.scentify.backend.service;

import com.scentify.backend.controller.AuthController.RegisterRequest;
import com.scentify.backend.model.User;
import com.scentify.backend.repository.UserRepository;
import com.scentify.backend.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

        public String login(String email, String password) {
            User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        //if (user.getPassword().equals(password)) {
            // ✅ Kirim role-nya ke token, bukan ID
           // return jwtUtil.generateToken(user.getEmail(), user.getRole());
        //}

            //if (passwordEncoder.matches(password, user.getPassword())) {
               // return jwtUtil.generateToken(user.getEmail(), user.getRole());
            //}
            if (user.getPassword().equals(password)) {                // (B)
                return jwtUtil.generateToken(user.getEmail(), user.getRole());
            }
            
            throw new RuntimeException("Invalid credentials");
        }

        public void register(RegisterRequest request) {
            if (userRepository.existsByEmail(request.email)) {
             throw new RuntimeException("Email already registered");
            }

            User user = new User();
            user.setNama(request.nama);
            user.setEmail(request.email);
            //user.setPassword(passwordEncoder.encode(request.password));
            user.setPassword(request.password);
            user.setAlamat(request.alamat);
            //user.setRole("BUYER"); // Atau "ADMIN", tergantung kebutuhan
            user.setRole(request.role); 

            userRepository.save(user);
        }

}
