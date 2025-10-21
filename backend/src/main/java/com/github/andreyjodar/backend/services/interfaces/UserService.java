package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.andreyjodar.backend.models.dtos.filter.UserFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.User;

public interface UserService extends UserDetailsService {
    User findById(Long id);
    User findByEmail(String email);
    Page<User> findFiltered(UserFilterDTO userFilterDTO, Pageable pageable);

    User commonCreate(UserCreationDTO userCreationDTO);
    User adminCreate(UserCreationDTO userCreationDTO);
    User update(Long id, UserUpdateDTO userUpdateDTO);
    SimpleResponseDTO delete(Long id);

    SimpleResponseDTO generateValidityCode(ForgotPasswordDTO forgotPasswordDTO);
    SimpleResponseDTO changePassword(ChangePasswordDTO changePasswordDTO);
}
