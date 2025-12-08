package com.github.andreyjodar.backend.services.implement;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.services.interfaces.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMail;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendSimpleEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setTo(to);
        simpleMail.setSubject(subject);
        simpleMail.setText(message);
        javaMail.send(simpleMail);
    }

    @Override
    @Async
    public void sendTemplateEmail(String to, String subject, Context context, String template) {
        String process = templateEngine.process(template, context);
        MimeMessage message = javaMail.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(process, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMail.send(message);
    }
    
}
