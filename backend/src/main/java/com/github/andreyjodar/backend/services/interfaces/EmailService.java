package com.github.andreyjodar.backend.services.interfaces;

import org.thymeleaf.context.Context;

public interface EmailService {
    public void sendSimpleEmail(String to, String subject, String message);
    public void sendTemplateEmail(String to, String subject, Context context, String template);
}
