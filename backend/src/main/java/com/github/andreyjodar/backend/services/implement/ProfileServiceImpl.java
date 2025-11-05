package com.github.andreyjodar.backend.services.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.models.dtos.request.ProfileCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.ProfileUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Profile;
import com.github.andreyjodar.backend.repositories.ProfileRepository;
import com.github.andreyjodar.backend.repositories.UserRepository;
import com.github.andreyjodar.backend.services.interfaces.ProfileService;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Profile findById(Long id) {
        return profileRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.profiles.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Profile findByRole(String role) {
        return profileRepository.findByRole(role)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.profiles.notfound",
                new Object[] { role }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profile> findAll(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    @Override
    @Transactional 
    public Profile create(ProfileCreationDTO profileCreationDTO) {
        String role = formatRole(profileCreationDTO.getRole());
        Profile profile = new Profile(role);
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public Profile update(Long id, ProfileUpdateDTO profileUpdateDTO) {
        Profile profile = findById(id);
        profile.setRole(formatRole(profileUpdateDTO.getRole()));
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Profile profile = findById(id);
        validateExistsUsers(profile);
        profileRepository.delete(profile);
    }

    private String formatRole(String role) {
        return role.trim()
            .toUpperCase(LocaleContextHolder.getLocale()) 
            .replaceAll("\\s+", "_");
    }

    private void validateExistsUsers(Profile profile) {
        if(userRepository.existsByProfilesId(profile.getId())) {
            throw new BusinessException(messageSource.getMessage("exception.profiles.hasusers",
                new Object[] { profile.getRole() }, LocaleContextHolder.getLocale()));
        }
    }
}