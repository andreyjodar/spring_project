package com.github.andreyjodar.backend.features.user.controller;

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

import com.github.andreyjodar.backend.features.user.model.Role;
import com.github.andreyjodar.backend.features.user.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfis")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<Role>> findAll(Pageable pageable) {
        return ResponseEntity.ok(roleService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody Role perfil) {
        return ResponseEntity.ok(roleService.create(perfil));
    }

    @PutMapping
    public ResponseEntity<Role> update(@Valid @RequestBody Role perfil) {
        return ResponseEntity.ok(roleService.update(perfil));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return ResponseEntity.ok("Excluído");
    }

}