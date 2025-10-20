package com.github.andreyjodar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleTextDTO;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.services.interfaces.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getuserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping(params = "email")
    public ResponseEntity<User> getuserById(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> create(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        return ResponseEntity.ok(userService.adminCreate(userCreationDTO));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.update(id, userUpdateDTO));
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleTextDTO> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
