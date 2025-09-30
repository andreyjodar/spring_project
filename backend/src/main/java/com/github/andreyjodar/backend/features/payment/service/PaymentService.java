package com.github.andreyjodar.backend.features.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.auction.service.AuctionService;
import com.github.andreyjodar.backend.features.bid.service.BidService;
import com.github.andreyjodar.backend.features.payment.model.Payment;
import com.github.andreyjodar.backend.features.bid.model.Bid;
import com.github.andreyjodar.backend.features.auction.model.Auction;
import com.github.andreyjodar.backend.features.payment.model.PaymentRequest;
import com.github.andreyjodar.backend.features.payment.repository.PaymentRepository;
import com.github.andreyjodar.backend.features.user.model.User;

@Service
public class PaymentService {

    @Autowired 
    private PaymentRepository paymentRepository;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;

    @Autowired 
    private MessageSource messageSource;

    Payment createPayment(PaymentRequest paymentRequest, User authUser) {
        Auction auction = auctionService.findById(paymentRequest.getAuctionId());

        if(!authUser.isBuyer() || authUser.equals(auction.getAuctioneer())) {
            throw new BusinessException(messageSource.getMessage("exception.payments.user",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        if(paymentRepository.findByAuction(auction).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.payments.haspayment",
                new Object[] { auction.getTitle() }, LocaleContextHolder.getLocale()));
        }

        Bid maxBid = bidService.findMaxBidByAuction(auction.getId());
        if(!authUser.equals(maxBid.getBidder())) {
            throw new BusinessException(messageSource.getMessage("exception.payments.notmaxbid",
                new Object[] { "Não houve nenhum lance" }, LocaleContextHolder.getLocale()));
        }

        Payment payment = new Payment(auction, maxBid.getBidValue(), authUser, "DONE");
        return paymentRepository.save(payment);
    }

    public void cancelPayment(Long paymentId, User authUser) {
        Payment payment = findById(paymentId);
        if(!authUser.isBuyer() || (authUser.isBuyer() && !payment.getBuyer().equals(authUser))) {
            throw new BusinessException(messageSource.getMessage("exception.payments.notvalid",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }

        paymentRepository.delete(payment);
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.payments.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Payment> findAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
