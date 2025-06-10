package com.github.andreyjodar.backend.service;

import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.model.Calculadora;

@Service
public class HelloService {
    public Calculadora somar(Calculadora calculadora) {
        calculadora.setResult(calculadora.getValue1() + calculadora.getValue2());
        return calculadora;
    }
}
