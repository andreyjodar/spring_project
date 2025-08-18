package com.github.andreyjodar.backend.shared.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.user.model.LoginRequest;
import com.github.andreyjodar.backend.features.user.model.LoginResponse;
import com.github.andreyjodar.backend.features.user.model.RegisterRequest;
import com.github.andreyjodar.backend.features.user.service.UserService;
import com.github.andreyjodar.backend.shared.services.AuthService;
import com.github.andreyjodar.backend.features.user.model.User;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest user) {
        User newUser = userService.fromDto(user);
        String password = newUser.getPassword();
        User userDb = userService.create(newUser);
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userDb.getEmail());
        loginRequest.setPassword(password);
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }
}
