package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.andreyjodar.backend.models.dtos.filter.PaymentFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.PaymentCreationDTO;
import com.github.andreyjodar.backend.models.entities.Payment;

public interface PaymentService {
    Payment findById(Long id);
    Page<Payment> findFiltered(PaymentFilterDTO PaymentFilterDTO, Pageable pageable);
    Payment create(PaymentCreationDTO paymentCreationDTO);
}
