package com.github.andreyjodar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.dto.PersonRequestDTO;
import com.github.andreyjodar.backend.security.JwtService;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String autenticar(PersonRequestDTO pessoa) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(pessoa.getEmail(), pessoa.getSenha()));

        return jwtService.generateToken(authentication.getName());
    }
}
