package com.github.andreyjodar.backend.features.user.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;
import com.github.andreyjodar.backend.shared.services.AuthService;
import com.github.andreyjodar.backend.shared.services.EmailService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired 
    private AuthService authService;

    public User create(User user) {
        user.setPassword(authService.encodePassword(user.getPassword()));
        user.setExpirationDate(LocalDate.now().plusMonths(1));
        user.setActive(true);

        User newUser = userRepository.save(user);
        sendSuccessEmail(newUser);
        return newUser;
    }

    private void sendSuccessEmail(User user) {
        Context context = new Context();
        context.setVariable("nome", user.getName());
        emailService.emailTemplate(user.getEmail(), "Cadastro Sucesso", context, "cadastroSucesso");
    }

    private void sendPasswordResetEmail(User user) {
        Context context = new Context();
        context.setVariable("code", user.getValidityCode());
        emailService.emailTemplate(user.getEmail(), "Código de Validação", context, "codigoValidacao");
    }

    public User update(User user) {
        User pessoaBanco = findById(user.getId());
        pessoaBanco.setName(user.getName());
        pessoaBanco.setEmail(user.getEmail());
        return userRepository.save(pessoaBanco);
    }

    public void generatePasswordResetCode(String email) {
        User userDb = findByEmail(email);
        String resetCode = authService.generateResetCode();
        userDb.setValidityCode(resetCode);
        userRepository.save(userDb);
        sendPasswordResetEmail(userDb);
    }

    public void resetPassword(String email, String code, String newPassword) {
        User user = findByEmail(email);
        if (!authService.isValidResetCode(user, code)) throw new IllegalArgumentException("Código inválido ou expirado");
        user.setPassword(authService.encodePassword(newPassword));
        user.setValidityCode(null);
        userRepository.save(user);
    }

    public void delete(Long id) {
        User pessoaBanco = findById(id);
        userRepository.delete(pessoaBanco);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.notfound",
                        new Object[] { email }, LocaleContextHolder.getLocale())));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}