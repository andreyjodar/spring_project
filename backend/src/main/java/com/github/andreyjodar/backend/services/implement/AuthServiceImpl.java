package com.github.andreyjodar.backend.services.implement;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.LoginDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.services.interfaces.AuthService;
import com.github.andreyjodar.backend.services.interfaces.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public String authenticate(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String accessToken = jwtService.generateToken(authentication.getName());
        return accessToken;
    }

    @Override
    public void register(UserCreationDTO userCreationDTO) {
        userService.commonCreate(userCreationDTO);
    }

    @Override
    public void sendRecoverCode(ForgotPasswordDTO forgotPasswordDTO) {
        userService.sendValidityCode(forgotPasswordDTO);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO);
    }
}
