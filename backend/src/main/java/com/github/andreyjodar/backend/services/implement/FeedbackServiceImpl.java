package com.github.andreyjodar.backend.services.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.mappers.FeedbackMapper;
import com.github.andreyjodar.backend.models.dtos.filter.FeedbackFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Feedback;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.FeedbackRepository;
import com.github.andreyjodar.backend.services.interfaces.FeedbackService;
import com.github.andreyjodar.backend.services.specification.FeedbackSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AuctionRepository auctionRepository;
    private final AuthUserProvider authUserProvider;
    private final FeedbackMapper feedbackMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Feedback findById(Long id) {
        return feedbackRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.feedbacks.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Feedback> findFiltered(FeedbackFilterDTO feedbackFilterDTO, Pageable pageable) {
        Specification<Feedback> spec = FeedbackSpecification.buildFilter(feedbackFilterDTO);
        return feedbackRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Feedback create(FeedbackCreationDTO feedbackCreationDTO) {
        User authUser = authUserProvider.getAuthUser();
        Auction auction = validateAuction(feedbackCreationDTO.getAuctionId());
        validateCreation(auction, authUser);
        Feedback feedback = feedbackMapper.toEntity(feedbackCreationDTO);
        return feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public Feedback update(Long id, FeedbackUpdateDTO feedbackUpdateDTO) {
        Feedback feedbackUpdate = findById(id);
        User authUser = authUserProvider.getAuthUser();
        validateOwner(feedbackUpdate, authUser);
        feedbackMapper.updateEntityFromDto(feedbackUpdateDTO, feedbackUpdate);
        return feedbackRepository.save(feedbackUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Feedback feedbackDelete = findById(id);
        User authUser = authUserProvider.getAuthUser();
        validateOwner(feedbackDelete, authUser);
        feedbackRepository.delete(feedbackDelete);
    }
    
    private Auction validateAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
                new Object[] { auctionId }, LocaleContextHolder.getLocale())));
    }

    private void validateCreation(Auction auction, User authUser) {
        if(auction.getAuctioneer().getId().equals(authUser.getId())) {
            throw new BusinessException(messageSource.getMessage("exception.feedbacks.isowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateOwner(Feedback feedback, User authUser) {
        if(!authUser.isAdmin() && !feedback.getAuthor().getId().equals(authUser.getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.feedback.notowner",
                new Object[] { authUser.getEmail() }, LocaleContextHolder.getLocale()));
        }
    }
}
