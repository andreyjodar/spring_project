package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;

import com.github.andreyjodar.backend.models.dtos.filter.FeedbackFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Feedback;

public interface FeedbackService {
    public Feedback findById(Long id);
    public Page<Feedback> findFiltered(FeedbackFilterDTO feedbackFilterDTO);
    public Feedback create(FeedbackCreationDTO feedbackCreationDTO);
    public Feedback update(FeedbackUpdateDTO feedbackUpdateDTO);
    public void delete(Long id);
}
