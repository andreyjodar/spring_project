package com.github.andreyjodar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.dto.PersonRequestDTO;
import com.github.andreyjodar.backend.service.AuthenticationService;

@RestController
@RequestMapping("/autenticacao")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestParam PersonRequestDTO person) {
        return authenticationService.autenticar(person);
    }
}
