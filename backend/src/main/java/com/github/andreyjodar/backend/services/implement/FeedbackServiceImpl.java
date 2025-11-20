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
import com.github.andreyjodar.backend.models.entities.Feedback;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.FeedbackRepository;
import com.github.andreyjodar.backend.services.interfaces.FeedbackService;
import com.github.andreyjodar.backend.services.specification.FeedbackSpecification;
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
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.feedback.notfound",
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
        validateAuction(feedbackCreationDTO.getAuctionId());
        Feedback feedback = feedbackMapper.toEntity(feedbackCreationDTO);
        return feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public Feedback update(Long id, FeedbackUpdateDTO feedbackUpdateDTO) {
        Feedback feedbackUpdate = findById(id);
        User authUser = authUserProvider.getAuthUser();
        validateAuthor(feedbackUpdate, authUser);
        feedbackMapper.updateEntityFromDto(feedbackUpdate, feedbackUpdateDTO);
        return feedbackRepository.save(feedbackUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Feedback feedbackDelete = findById(id);
        feedbackRepository.delete(feedbackDelete);
    }
    
    private void validateAuction(Long auctionId) {
        if(!auctionRepository.existsById(auctionId)) {
            throw new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
                new Object[] { auctionId }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateAuthor(Feedback feedback, User authUser) {
        // finalizar validacao
    }
}
