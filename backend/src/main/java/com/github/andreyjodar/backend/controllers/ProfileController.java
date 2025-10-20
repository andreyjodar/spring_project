package com.github.andreyjodar.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.github.andreyjodar.backend.models.dtos.request.ProfileCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.ProfileUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleTextDTO;
import com.github.andreyjodar.backend.models.entities.Profile;
import com.github.andreyjodar.backend.services.interfaces.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired 
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<Page<Profile>> getAllProfiles(Pageable pageable) {
        return ResponseEntity.ok(profileService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @GetMapping(params = "role")
    public ResponseEntity<Profile> getProfileByRole(@RequestParam("role") String role) {
        return ResponseEntity.ok(profileService.findByRole(role));
    }

    @PostMapping 
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Profile> create(@Valid @RequestBody ProfileCreationDTO profileCreationDTO) {
        return ResponseEntity.ok(profileService.create(profileCreationDTO));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Profile> update(@PathVariable("id") Long id, @Valid @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        return ResponseEntity.ok(profileService.update(id, profileUpdateDTO));
    }

    @DeleteMapping("/{id}") 
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleTextDTO> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

}
