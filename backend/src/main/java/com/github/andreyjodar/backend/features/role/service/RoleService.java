package com.github.andreyjodar.backend.features.role.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.ForbiddenException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.role.model.Role;
import com.github.andreyjodar.backend.features.role.repository.RoleRepository;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserRepository userRepository;

    public Role createRole(User authUser, String type) {
        validateOperation(authUser);
        String formattedType = formatRoleType(type);
        validateRole(formattedType);
        Role role = new Role(formattedType);
        return roleRepository.save(role);
    }

    public void deleteRole(Long id, User authUser) {
        validateOperation(authUser);
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findByType(String type) {
        return roleRepository.findByType(type)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.roles.notfound",
                new Object[] { type }, LocaleContextHolder.getLocale())));
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.roles.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }
    
    private void validateRole(String type) {
        if(roleRepository.findByType(type).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.roles.existtype",
                new Object[] { type }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateOperation(User authUser) {
        if(!authUser.isAdmin()) {
            throw new ForbiddenException(messageSource.getMessage("exception.roles.forbidden",
                new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
        }
    }

    private String formatRoleType(String type) {
        return type.trim()
            .toUpperCase(LocaleContextHolder.getLocale()) 
            .replaceAll("\\s+", "_");
    }
}
