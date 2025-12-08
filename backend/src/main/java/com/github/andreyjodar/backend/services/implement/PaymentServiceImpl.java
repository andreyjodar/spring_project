package com.github.andreyjodar.backend.services.implement;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.filter.PaymentFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.PaymentCreationDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Payment;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.PaymentRepository;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;
import com.github.andreyjodar.backend.services.interfaces.PaymentService;
import com.github.andreyjodar.backend.services.specification.PaymentSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AuctionService auctionService;
    private final AuthUserProvider authUserProvider;
    private final MessageSource messageSource;
    
    @Override
    @Transactional(readOnly = true)
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.payments.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Payment> findFiltered(PaymentFilterDTO PaymentFilterDTO, Pageable pageable) {
        Specification<Payment> spec = PaymentSpecification.buildFilter(PaymentFilterDTO);
        return paymentRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Payment create(PaymentCreationDTO paymentCreationDTO) {
        User authUser = authUserProvider.getAuthUser();
        Auction auctuion = auctionService.findById(paymentCreationDTO.getAuctionId());
        validateIsAuctionEnd(auctuion);
        validateHasPayment(auctuion.getId());
        validateTopBidder(auctuion, authUser);
        Payment payment = new Payment(auctuion, auctuion.getCurrentPrice(), authUser);
        return paymentRepository.save(payment);
    }

    private void validateTopBidder(Auction auction, User authUser) {
        if(!auction.getCurrentBidder().equals(authUser.getEmail())) {
            throw new BusinessException(messageSource.getMessage("exception.payments.nottopbider",
                new Object[] { authUser.getEmail(), auction.getId() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateIsAuctionEnd(Auction auction) {
        if(auction.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageSource.getMessage("exception.payments.notendauction",
                new Object[] { auction.getId() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasPayment(Long auctionId) {
        if(paymentRepository.existsByAuctionId(auctionId)) {
            throw new BusinessException(messageSource.getMessage("exception.payments.haspayment",
                new Object[] { auctionId }, LocaleContextHolder.getLocale()));
        }
    }
    
}
