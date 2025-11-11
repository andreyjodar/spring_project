package com.github.andreyjodar.backend.services.implement;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.models.dtos.filter.FeedbackFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Feedback;
import com.github.andreyjodar.backend.repositories.FeedbackRepository;
import com.github.andreyjodar.backend.services.interfaces.FeedbackService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    @Transactional(readOnly = true)
    public Feedback findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Feedback> findFiltered(FeedbackFilterDTO feedbackFilterDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findFiltered'");
    }

    @Override
    @Transactional
    public Feedback create(FeedbackCreationDTO feedbackCreationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    @Transactional
    public Feedback update(FeedbackUpdateDTO feedbackUpdateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
