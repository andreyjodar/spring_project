package com.github.andreyjodar.backend.features.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.user.model.Role;
import com.github.andreyjodar.backend.features.user.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MessageSource messageSource;

    public Role create(Role role) {
        Role perfilCadastrado = roleRepository.save(role);

        return perfilCadastrado;
    }

    public Role update(Role role) {
        // return roleRepository.save(pessoa);
        Role perfilBanco = findById(role.getId());
        perfilBanco.setName(role.getName());
        return roleRepository.save(perfilBanco);
    }

    public void delete(Long id) {
        Role perfilBanco = findById(id);
        roleRepository.delete(perfilBanco);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("role.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

}
