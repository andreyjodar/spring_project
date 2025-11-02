package com.github.andreyjodar.backend.services.implement;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.services.interfaces.RandomGenerator;

@Component
public class RandomStringGenerator implements RandomGenerator {
    private static final String ALPHANUMERIC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generateRandomAlphanumeric(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHANUMERIC_CHARS.length());
            stringBuilder.append(ALPHANUMERIC_CHARS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
}
