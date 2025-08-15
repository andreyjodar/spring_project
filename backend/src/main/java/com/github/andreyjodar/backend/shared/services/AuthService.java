package com.github.andreyjodar.backend.shared.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.UserRequest;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public String authenticate(UserRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        return jwtService.generateToken(authentication.getName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrada"));
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public Boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateResetCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public boolean isValidResetCode(User user, String code) {
        return code != null && 
               code.equals(user.getValidityCode()) && 
               isCodeNotExpired(user);
    }

    private boolean isCodeNotExpired(User user) {
        return true; // placeholder
    }
}
