package com.github.andreyjodar.backend.features.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.auth.model.ChangePasswordRequest;
import com.github.andreyjodar.backend.features.auth.model.ForgotPasswordRequest;
import com.github.andreyjodar.backend.features.auth.model.LoginRequest;
import com.github.andreyjodar.backend.features.auth.model.RegisterRequest;
import com.github.andreyjodar.backend.core.model.ApiResponse;
import com.github.andreyjodar.backend.features.auth.model.AuthResponse;
import com.github.andreyjodar.backend.features.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        authService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok(new ApiResponse("Reset Password Code was Invited by Email"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new ApiResponse("User Password was Changed"));
    }
}
