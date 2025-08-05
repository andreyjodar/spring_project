package com.github.andreyjodar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.exception.NotFoundException;
import com.github.andreyjodar.backend.model.Leilao;
import com.github.andreyjodar.backend.repository.LeilaoRepository;

@Service
public class LeilaoService {
    @Autowired
    LeilaoRepository leilaoRepository;

    @Autowired
    MessageSource messageSource;

    public Leilao inserir(Leilao leilao) {
        Leilao leilaoAtual = leilaoRepository.save(leilao);
        return leilaoAtual;
    }

    public Leilao alterar(Leilao leilao) {
        Leilao leilaoBanco = buscarPorId(leilao.getId());
        leilaoBanco.setCategoria(leilao.getCategoria());
        leilaoBanco.setTitulo(leilao.getTitulo());
        leilaoBanco.setDescricao(leilao.getDescricao());
        leilaoBanco.setDescricaoDetalhada(leilao.getDescricaoDetalhada());
        leilaoBanco.setDataHoraInicio(leilao.getDataHoraInicio());
        leilaoBanco.setDataHoraFim(leilao.getDataHoraFim());
        leilaoBanco.setStatus(leilao.getStatus());
        leilaoBanco.setValorIncremento(leilao.getValorIncremento());
        leilaoBanco.setLanceMinimo(leilao.getLanceMinimo());
        return leilaoRepository.save(leilaoBanco);
    }

    public void excluir(Long id) {
        Leilao leilaoBanco = buscarPorId(id);
        leilaoRepository.delete(leilaoBanco); 
    }

    public Leilao buscarPorId(Long id) {
        return leilaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Leilao> buscarTodos(Pageable pageable) {
        return leilaoRepository.findAll(pageable);
    }

}
