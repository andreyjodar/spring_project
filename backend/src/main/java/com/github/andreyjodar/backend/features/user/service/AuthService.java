package com.github.andreyjodar.backend.features.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.LoginRequest;
import com.github.andreyjodar.backend.features.user.model.LoginResponse;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticate(LoginRequest user) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            User userDb = userRepository.findByEmail(user.getEmail()).get();
            String token = jwtService.generateToken(authentication.getName());
            LoginResponse loginResponse = new LoginResponse(token, userDb);

        return loginResponse;
    }
}
