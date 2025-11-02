package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.andreyjodar.backend.models.dtos.request.ProfileCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.ProfileUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Profile;

public interface ProfileService {
    Profile findById(Long id);
    Profile findByRole(String role);
    Page<Profile> findAll(Pageable pageable);
    Profile create(ProfileCreationDTO profileCreationDTO);
    Profile update(Long id, ProfileUpdateDTO profileUpdateDTO);
    void delete(Long id);
}
