package com.github.andreyjodar.backend.features.user.service;

import java.time.LocalDate;
import java.util.Set;

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
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.user.model.RegisterUserRequest;
import com.github.andreyjodar.backend.features.user.model.Role;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.repository.UserRepository;
import com.github.andreyjodar.backend.shared.services.EmailService;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private RoleService roleService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;

    @Autowired @Lazy
    PasswordEncoder passwordEncoder;

    public User create(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setExpirationDate(LocalDate.now().plusMonths(1));
        user.setActive(true);
        User newUser = userRepository.save(user);
        sendSuccessEmail(newUser);
        return newUser;
    }

    private void sendSuccessEmail(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName());
        emailService.emailTemplate(user.getEmail(), "Cadastro Sucesso", context, "successfully-register");
    }

    public User update(User user) {
        User pessoaBanco = findById(user.getId());
        pessoaBanco.setName(user.getName());
        pessoaBanco.setEmail(user.getEmail());
        return userRepository.save(pessoaBanco);
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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.notfound",
                        new Object[] { email }, LocaleContextHolder.getLocale())));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User fromDto(RegisterUserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BusinessException("Email já está sendo utilizado");
        }

        User newUser = new User();
        Set<Role> roles = roleService.findRolesByNames(userRequest.getRoles());

        newUser.setName(userRequest.getName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());
        newUser.setRoles(roles);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrada"));
    }

}