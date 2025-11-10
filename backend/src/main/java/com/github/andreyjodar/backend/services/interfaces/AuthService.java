package com.github.andreyjodar.backend.services.interfaces;

import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.LoginDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;

public interface AuthService {
    String authenticate(LoginDTO loginDTO);
    void register(UserCreationDTO userCreationDTO);
    void sendRecoverCode(ForgotPasswordDTO forgotPasswordDTO);
    void changePassword(ChangePasswordDTO changePasswordDTO);
}
