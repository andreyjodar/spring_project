package com.github.andreyjodar.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.github.andreyjodar.backend.exception.NotFoundException;
import com.github.andreyjodar.backend.model.Perfil;
import com.github.andreyjodar.backend.repository.PerfilRepository;

public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MessageSource messageSource;

    public Perfil inserir(Perfil perfil) {
        Perfil perfilCadastrado = perfilRepository.save(perfil);

        return perfilCadastrado;
    }

    public Perfil alterar(Perfil perfil) {
        // return perfilRepository.save(pessoa);
        Perfil perfilBanco = buscarPorId(perfil.getId());
        perfilBanco.setNome(perfil.getNome());
        return perfilRepository.save(perfilBanco);
    }

    public void excluir(Long id) {
        Perfil perfilBanco = buscarPorId(id);
        perfilRepository.delete(perfilBanco);
    }

    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("perfil.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Perfil> buscarTodos(Pageable pageable) {
        return perfilRepository.findAll(pageable);
    }

}
