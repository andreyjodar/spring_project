package com.github.andreyjodar.backend.services.interfaces;

import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.LoginDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.response.AccessTokenDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;

public interface AuthService {
    AccessTokenDTO authenticate(LoginDTO loginDTO);
    SimpleResponseDTO register(UserCreationDTO userCreationDTO);
    SimpleResponseDTO sendRecoverCode(ForgotPasswordDTO forgotPasswordDTO);
    SimpleResponseDTO changePassword(ChangePasswordDTO changePasswordDTO);
}
