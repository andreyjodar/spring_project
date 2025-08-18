package com.github.andreyjodar.backend.shared.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.user.model.LoginUserRequest;
import com.github.andreyjodar.backend.features.user.model.RegisterUserRequest;
import com.github.andreyjodar.backend.features.user.service.UserService;
import com.github.andreyjodar.backend.shared.services.AuthService;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.UserAuthDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDto> login(@RequestBody LoginUserRequest user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest user) {
        User newUser = userService.fromDto(user);
        return ResponseEntity.ok(userService.create(newUser));
    }
}
