package com.github.andreyjodar.backend.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.filter.PaymentFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.PaymentCreationDTO;
import com.github.andreyjodar.backend.models.entities.Payment;
import com.github.andreyjodar.backend.services.interfaces.PaymentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Payment> create(@RequestBody @Valid PaymentCreationDTO paymentCreationDTO) {
        return ResponseEntity.ok(paymentService.create(paymentCreationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Payment>> getFiltered(PaymentFilterDTO paymentFilterDTO, Pageable pageable) {
        return ResponseEntity.ok(paymentService.findFiltered(paymentFilterDTO, pageable));
    }
    
}
