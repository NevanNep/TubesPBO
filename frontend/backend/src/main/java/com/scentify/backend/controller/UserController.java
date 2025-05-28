package com.scentify.backend.controller;

import com.scentify.backend.model.User;
import com.scentify.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> result = userService.login(user.getEmail(), user.getPassword());
        return result.isPresent() ? "Login Berhasil" : "Login Gagal";
    }
}
