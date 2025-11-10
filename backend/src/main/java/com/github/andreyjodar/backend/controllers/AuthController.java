package com.github.andreyjodar.backend.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(new AccessTokenDTO(authService.authenticate(loginDTO)));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponseDTO> register(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        authService.register(userCreationDTO);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.users.create",
            new Object[] { userCreationDTO.getEmail() }, LocaleContextHolder.getLocale())));
    }

    @PostMapping("/recover-password")
    public ResponseEntity<SimpleResponseDTO> sendRecoverCode(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        authService.sendRecoverCode(forgotPasswordDTO);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.users.recovercode",
            new Object[] { forgotPasswordDTO.getEmail() }, LocaleContextHolder.getLocale())));
    }

    @PostMapping("/change-password")
    public ResponseEntity<SimpleResponseDTO> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        authService.changePassword(changePasswordDTO);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.users.passwordchange",
            new Object[] { changePasswordDTO.getEmail() }, LocaleContextHolder.getLocale())));
    }

}
