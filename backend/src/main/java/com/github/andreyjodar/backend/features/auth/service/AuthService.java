package com.github.andreyjodar.backend.features.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.features.user.mapper.UserMapper;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.model.UserResponse;
import com.github.andreyjodar.backend.features.auth.model.AuthResponse;
import com.github.andreyjodar.backend.features.auth.model.ChangePasswordRequest;
import com.github.andreyjodar.backend.features.auth.model.ForgotPasswordRequest;
import com.github.andreyjodar.backend.features.auth.model.LoginRequest;
import com.github.andreyjodar.backend.features.auth.model.RegisterRequest;
import com.github.andreyjodar.backend.features.user.service.UserService;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public AuthResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            String accessToken = jwtService.generateToken(authentication.getName());
            User user = userService.findByEmail(loginRequest.getEmail());
            UserResponse userResponse = userMapper.fromEntity(user);

        return new AuthResponse(accessToken, userResponse);
    }

    public AuthResponse register(RegisterRequest userRequest) {
        userService.createUser(userRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userRequest.getEmail());
        loginRequest.setPassword(userRequest.getPassword());
        return authenticate(loginRequest);
    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        userService.generateValidityCode(forgotPasswordRequest.getEmail());
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        userService.resetPassword(changePasswordRequest.getEmail(), changePasswordRequest.getValidityCode(), changePasswordRequest.getNewPassword());
    }
}
