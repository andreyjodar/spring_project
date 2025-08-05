package com.github.andreyjodar.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.model.Pessoa;
import com.github.andreyjodar.backend.service.PessoaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<Pessoa>> buscarTodos(Pageable pageable) {
        return ResponseEntity.ok(pessoaService.buscarTodos(pageable));
    }

    @PostMapping
    public ResponseEntity<Pessoa> inserir(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.inserir(pessoa));
    }

    @PutMapping
    public ResponseEntity<Pessoa> alterar(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.alterar(pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        pessoaService.excluir(id);
        return ResponseEntity.ok("Excluído");
    }

}