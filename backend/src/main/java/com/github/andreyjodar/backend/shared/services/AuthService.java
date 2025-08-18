package com.github.andreyjodar.backend.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.UserAuthDto;
import com.github.andreyjodar.backend.features.user.model.LoginUserRequest;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public UserAuthDto authenticate(LoginUserRequest user) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            User userDb = userRepository.findByEmail(user.getEmail()).get();
            UserAuthDto userAuthDto = new UserAuthDto();
            userAuthDto.setEmail(userDb.getEmail());
            userAuthDto.setName(userDb.getName());
            userAuthDto.setToken(jwtService.generateToken(authentication.getName()));

        return userAuthDto;
    }
}
