package com.github.andreyjodar.backend.features.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.user.model.Role;
import com.github.andreyjodar.backend.features.user.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MessageSource messageSource;

    public Role create(Role role) {
        if(roleRepository.findByName(role.getName().toUpperCase()).isPresent()) {
            throw new BusinessException("Role " + role.getName().toUpperCase() + "já existe");
        }
        
        role.setName(role.getName().toUpperCase());
        Role roleDb = roleRepository.save(role);

        return roleDb;
    }

    public Role update(Role role) {
        Role roleDb = findById(role.getId());
        roleDb.setName(role.getName().toUpperCase());
        return roleRepository.save(roleDb);
    }

    public void delete(Long id) {
        Role roleDb = findById(id);
        roleRepository.delete(roleDb);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("role.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Role findNyName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("role.notfound", 
                        new Object[] { name }, LocaleContextHolder.getLocale())));
    }

    public Set<Role> findRolesByNames(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        
        for (String roleName : roleNames) {
            Role role = findNyName(roleName);
            roles.add(role);
        }

        return roles;
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @PostConstruct
    private void initDefaultRoles() {
        createInitRole("ADMIN");
        createInitRole("SELLER");
        createInitRole("BUYER");
    }

    private void createInitRole(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Role criada: " + roleName);
        }
    }

}
