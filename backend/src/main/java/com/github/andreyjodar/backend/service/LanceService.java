package com.github.andreyjodar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.exception.NotFoundException;
import com.github.andreyjodar.backend.model.Lance;
import com.github.andreyjodar.backend.model.Leilao;
import com.github.andreyjodar.backend.repository.LanceRepository;

@Service
public class LanceService {
    
    @Autowired
    LanceRepository lanceRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    EmailService emailService;

    @Autowired
    LeilaoService leilaoService;

    public Lance inserir(Lance lance) {
        Leilao leilao = leilaoService.buscarPorId(lance.getLeilao().getId());
        
    }

    public void excluir(Long id) {

    }

    public Lance buscarPorId(Long id) {
        return lanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Lance> buscarTodos(Pageable pageable) {

    }
}
