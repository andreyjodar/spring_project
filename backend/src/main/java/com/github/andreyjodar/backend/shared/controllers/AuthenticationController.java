package com.github.andreyjodar.backend.shared.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.user.model.UserRequest;
import com.github.andreyjodar.backend.features.user.service.UserService;
import com.github.andreyjodar.backend.shared.services.AuthenticationService;
import com.github.andreyjodar.backend.features.user.model.User;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam UserRequest user) {
        return authenticationService.authenticate(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User newUser) {
        User registeredUser = userService.create(newUser);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
