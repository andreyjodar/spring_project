package com.github.andreyjodar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.exception.NotFoundException;
import com.github.andreyjodar.backend.model.Feedback;
import com.github.andreyjodar.backend.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    public Feedback inserir(Feedback feedback) {
        Feedback feedbackAtual = feedbackRepository.save(feedback);
        enviarEmailFeedback(feedbackAtual);
        return feedbackAtual;
    }

    public Feedback alterar(Feedback feedback) {
        Feedback feedbackBanco = buscarPorId(feedback.getId());
        feedbackBanco.setComentario(feedback.getComentario());
        feedbackBanco.setNota(feedback.getNota());
        feedbackBanco.setDataHora(feedback.getDataHora());
        return feedbackRepository.save(feedbackBanco);
    }

    public void enviarEmailFeedback(Feedback feedback) {
        Context context = new Context();
        context.setVariable("nome", feedback.getAutor());
        emailService.emailTemplate(feedback.getDestinatario().getNome(), "Recebeu um Feedback", context, feedback.getComentario());
    }

    public void excluir(Long id) {
        Feedback feedbackBanco = buscarPorId(id);
        feedbackRepository.delete(feedbackBanco);
    }

    public Feedback buscarPorId(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("feedback.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Feedback> buscarTodos(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }
}