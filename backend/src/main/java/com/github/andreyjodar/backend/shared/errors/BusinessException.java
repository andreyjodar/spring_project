package com.github.andreyjodar.backend.shared.errors;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
