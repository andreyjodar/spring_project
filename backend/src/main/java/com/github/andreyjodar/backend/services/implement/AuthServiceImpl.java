package com.github.andreyjodar.backend.services.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.github.andreyjodar.backend.core.security.JwtService;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.LoginDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.response.AccessTokenDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.services.interfaces.AuthService;
import com.github.andreyjodar.backend.services.interfaces.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final MessageSource messageSource;

    @Override
    public AccessTokenDTO authenticate(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            String accessToken = jwtService.generateToken(authentication.getName());
        return new AccessTokenDTO(accessToken);
    }

    @Override
    public SimpleResponseDTO register(UserCreationDTO userCreationDTO) {
        User userCreate = userService.commonCreate(userCreationDTO);

        return new SimpleResponseDTO(messageSource.getMessage("success.users.create",
            new Object[] { userCreate.getEmail() }, LocaleContextHolder.getLocale()));
    }

    @Override
    public SimpleResponseDTO sendRecoverCode(ForgotPasswordDTO forgotPasswordDTO) {
        userService.sendValidityCode(forgotPasswordDTO);

        return new SimpleResponseDTO(messageSource.getMessage("success.users.recovercode",
            new Object[] { forgotPasswordDTO.getEmail() }, LocaleContextHolder.getLocale()));
    }

    @Override
    public SimpleResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO);

        return new SimpleResponseDTO(messageSource.getMessage("success.users.passwordchange",
            new Object[] { changePasswordDTO.getEmail() }, LocaleContextHolder.getLocale()));
    }
}
