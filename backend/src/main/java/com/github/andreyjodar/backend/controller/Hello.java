package com.github.andreyjodar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.model.Calculadora;
import com.github.andreyjodar.backend.service.HelloService;

@RestController
public class Hello {

    @Autowired
    private HelloService helloService;

    @GetMapping("/")
    public String hello() {
        return "Olá, Spring!";
    }

    @GetMapping("/somar")
    public Integer somar(@RequestParam("v1") Integer valor1, @RequestParam("v2") Integer valor2) {
        return valor1 + valor2;
    }

    @PostMapping("/somar")
    public Calculadora somar(@RequestBody Calculadora calculadora) {
        return helloService.somar(calculadora);
    }
}
