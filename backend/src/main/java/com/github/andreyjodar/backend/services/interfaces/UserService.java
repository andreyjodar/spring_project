package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.andreyjodar.backend.models.dtos.filter.UserFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.entities.User;

public interface UserService extends UserDetailsService {
    User findById(Long id);
    User findByEmail(String email);
    Page<User> findFiltered(UserFilterDTO userFilterDTO, Pageable pageable);

    User commonCreate(UserCreationDTO userCreationDTO);
    User adminCreate(UserCreationDTO userCreationDTO);
    User update(Long id, UserUpdateDTO userUpdateDTO);
    void delete(Long id);

    void sendValidityCode(ForgotPasswordDTO forgotPasswordDTO);
    void changePassword(ChangePasswordDTO changePasswordDTO);
}
