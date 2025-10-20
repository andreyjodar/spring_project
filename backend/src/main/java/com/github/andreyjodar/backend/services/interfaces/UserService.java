package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleTextDTO;
import com.github.andreyjodar.backend.models.entities.User;

public interface UserService extends UserDetailsService {
    User findById(Long id);
    User findByEmail(String email);
    User commonCreate(UserCreationDTO userCreationDTO);
    User adminCreate(UserCreationDTO userCreationDTO);
    User update(Long id, UserUpdateDTO userUpdateDTO);
    SimpleTextDTO delete(Long id);
}
