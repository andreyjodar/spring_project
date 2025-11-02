package com.github.andreyjodar.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.LoginDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.response.AccessTokenDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.services.interfaces.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponseDTO> register(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        return ResponseEntity.ok(authService.register(userCreationDTO));
    }

    @PostMapping("/recover-password")
    public ResponseEntity<SimpleResponseDTO> sendRecoverCode(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return ResponseEntity.ok(authService.sendRecoverCode(forgotPasswordDTO));
    }

    @PostMapping("/change-password")
    public ResponseEntity<SimpleResponseDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        return ResponseEntity.ok(authService.changePassword(changePasswordDTO));
    }

}
