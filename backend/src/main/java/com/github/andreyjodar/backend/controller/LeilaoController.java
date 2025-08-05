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

import com.github.andreyjodar.backend.model.Leilao;
import com.github.andreyjodar.backend.service.LeilaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/leiloes")
public class LeilaoController {

    @Autowired
    LeilaoService leilaoService;

    @GetMapping
    public ResponseEntity<Page<Leilao>> buscarTodos(Pageable pageable) {
        return ResponseEntity.ok(leilaoService.buscarTodos(pageable));
    }

    @PostMapping
    public ResponseEntity<Leilao> inserir(@Valid @RequestBody Leilao feedback) {
        return ResponseEntity.ok(leilaoService.inserir(feedback));
    }

    @PutMapping
    public ResponseEntity<Leilao> alterar(@Valid @RequestBody Leilao feedback) {
        return ResponseEntity.ok(leilaoService.alterar(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        leilaoService.excluir(id);
        return ResponseEntity.ok("Excluído");
    }
}
