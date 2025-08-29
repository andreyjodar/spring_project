package com.github.andreyjodar.backend.features.user.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.ForbiddenException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auth.model.RegisterRequest;
import com.github.andreyjodar.backend.features.user.mapper.UserMapper;
import com.github.andreyjodar.backend.features.user.model.EditUserRequest;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;
import com.github.andreyjodar.backend.shared.services.EmailService;

@Service
public class UserService implements UserDetailsService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired @Lazy
    private PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.users.existemail",
                new Object[] { userRequest.getEmail() }, LocaleContextHolder.getLocale()));
        }

        User user = userMapper.fromDto(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sendRegisterSuccess(user);
        return userRepository.save(user);
    }

    private void sendRegisterSuccess(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        emailService.sendTemplateEmail(user.getEmail(), "Register Success", context, "register-success");
    }

    private void sendValidityCode(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("validityCode", user.getValidityCode());
        emailService.sendTemplateEmail(user.getEmail(), "Change Password Code", context, "validitycode-changepassw");
    }

    public User updateUser(Long id, User authUser, EditUserRequest editUserRequest) {
        if(!authUser.isAdmin() && !authUser.getId().equals(id)) {
            throw new ForbiddenException(messageSource.getMessage("exception.users.forbidden",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        User user = findById(id);
        user.setName(editUserRequest.getName());
        return userRepository.save(user);
    }

    public void generateValidityCode(String email) {
        User user = findByEmail(email);
        String validityCode = generateCode(6);
        user.setValidityCode(validityCode);
        user.setExpirationDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        sendValidityCode(user);
    }

    private String generateCode(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            code.append(CHAR_POOL.charAt(index));
        }
        return code.toString();
    }

    public void resetPassword(String email, String validityCode, String newPassword) {
        User user = findByEmail(email);

        if(user.getValidityCode() == null || !user.getValidityCode().equals(validityCode)) {
            throw new BusinessException(messageSource.getMessage("exception.users.invalidcode", 
                new Object [] { validityCode }, LocaleContextHolder.getLocale()));
        }

        if(user.getExpirationDate() == null || user.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageSource.getMessage("exception.users.expiredcode", 
                new Object [] { validityCode }, LocaleContextHolder.getLocale()));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setValidityCode(null);
        user.setExpirationDate(null);
        userRepository.save(user);
    }

    public void deleteUser(Long id, User authUser) {
        if(!authUser.isAdmin()) {
            throw new ForbiddenException(messageSource.getMessage("exception.users.forbidden", 
                new Object [] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        User user = findById(id);
        userRepository.delete(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.users.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.users.notfound",
                new Object[] { email }, LocaleContextHolder.getLocale())));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrada"));
    }
}