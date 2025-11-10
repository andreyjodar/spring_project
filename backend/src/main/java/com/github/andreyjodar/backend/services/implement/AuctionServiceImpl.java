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
import com.github.andreyjodar.backend.mappers.AuctionMapper;
import com.github.andreyjodar.backend.models.dtos.filter.AuctionFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.models.enums.AuctionStatus;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.BidRepository;
import com.github.andreyjodar.backend.repositories.CategoryRepository;
import com.github.andreyjodar.backend.repositories.FeedbackRepository;
import com.github.andreyjodar.backend.repositories.PaymentRepository;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;
import com.github.andreyjodar.backend.services.specification.AuctionSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private final FeedbackRepository feedbackRepository;
    private final PaymentRepository paymentRepository;
    private final BidRepository bidRepository;
    private final AuthUserProvider authUserProvider;
    private final MessageSource messageSource;
    private final AuctionMapper auctionMapper;

    @Override
    @Transactional(readOnly = true)
    public Auction findById(Long id) {
        return auctionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Auction> findFiltered(AuctionFilterDTO auctionFilterDTO, Pageable pageable) {
        Specification<Auction> spec = AuctionSpecification.buildFilter(auctionFilterDTO);
        return auctionRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Auction create(AuctionCreationDTO auctionCreationDTO) {
        validatePeriod(auctionCreationDTO.getStartDateTime(), auctionCreationDTO.getEndDateTime());
        validateCategory(auctionCreationDTO.getCategoryId());
        validateCreation(authUserProvider.getAuthUser());
        Auction auction = auctionMapper.toEntity(auctionCreationDTO);
        return auctionRepository.save(auction);
    }

    @Override
    @Transactional
    public Auction update(Long id, AuctionUpdateDTO auctionUpdateDTO) {
        Auction auctionUpdate = findById(id);
        validateUpdate(authUserProvider.getAuthUser(), auctionUpdate);
        validateActive(auctionUpdate);
        validateUpdateDateTime(auctionUpdate, auctionUpdateDTO);
        validateUpdateMinBid(auctionUpdate,auctionUpdateDTO);
        auctionMapper.updateEntityFromDTO(auctionUpdateDTO, auctionUpdate);
        return auctionRepository.save(auctionUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Auction auctionDelete = findById(id);
        validateDelete(authUserProvider.getAuthUser(), auctionDelete);
        validateHasBid(auctionDelete.getId());
        validateHasFeedback(auctionDelete.getId());
        validateHasPayment(auctionDelete.getId());
        auctionRepository.delete(auctionDelete);
    }

    private void validatePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.auctions.invalidperiod",
                new Object[] { startDateTime, endDateTime }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateCategory(Long id) {
        if(categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("exception.categories.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateCreation(User authUser) {
        if(!authUser.isSeller()) {
            throw new ForbiddenException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateDelete(User authUser, Auction auction) {
        if(!authUser.isAdmin() && !authUser.isSeller()) {
            throw new ForbiddenException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }
        if(!authUser.isAdmin() && authUser.isSeller() && !authUser.getId().equals(auction.getAuctioneer().getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.auctions.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale())); 
        }
    }

    private void validateUpdate(User authUser, Auction auction) {
        if(!authUser.isAdmin() && !authUser.isSeller()) {
            throw new ForbiddenException(messageSource.getMessage("exception.auctions.notseller",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }
        if(!authUser.isAdmin() && authUser.isSeller() && !authUser.getId().equals(auction.getAuctioneer().getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.auctions.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale())); 
        }
    }

    private void validateHasBid(Long id) {
        if(bidRepository.existsByAuctionId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.hasbid",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }
    
    private void validateHasFeedback(Long id) {
        if(feedbackRepository.existsByAuctionId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.hasfeedback",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasPayment(Long id) {
        if(paymentRepository.existsByAuctionId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.haspayment",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateActive(Auction auction) {
        if (auction.getStatus().equals(AuctionStatus.CLOSED)) {
            throw new BusinessException(messageSource.getMessage("exception.auctions.blockupdateclosed",
                new Object[] { auction.getStartDateTime() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateUpdateDateTime(Auction auction, AuctionUpdateDTO auctionUpdateDTO) {
        if (auction.getStartDateTime().isBefore(LocalDateTime.now())) {
            if (auctionUpdateDTO.getStartDateTime() != null && !auctionUpdateDTO.getStartDateTime().equals(auction.getStartDateTime())) {
                throw new BusinessException(messageSource.getMessage("exception.auctions.blockupdatestartdate",
                    new Object[] { auction.getStartDateTime() }, LocaleContextHolder.getLocale()));
            }
            if (auctionUpdateDTO.getEndDateTime() != null && !auctionUpdateDTO.getEndDateTime().equals(auction.getEndDateTime())) {
                throw new BusinessException(messageSource.getMessage("exception.auctions.blockupdateenddate",
                    new Object[] { auction.getEndDateTime() }, LocaleContextHolder.getLocale()));
            }
        }
        if (auctionUpdateDTO.getStartDateTime() != null && auctionUpdateDTO.getEndDateTime() != null) {
            validatePeriod(auctionUpdateDTO.getStartDateTime(), auctionUpdateDTO.getEndDateTime());
        }
    }

    private void validateUpdateMinBid(Auction auction, AuctionUpdateDTO auctionUpdateDTO) {
        if (bidRepository.existsByAuctionId(auction.getId())) {
            if (auctionUpdateDTO.getMinBid() != null && !auctionUpdateDTO.getMinBid().equals(auction.getMinBid())) {
                throw new BusinessException(messageSource.getMessage("exception.auctions.blockupdateminbid",
                    null, LocaleContextHolder.getLocale()));
            }
        }
    }

}
