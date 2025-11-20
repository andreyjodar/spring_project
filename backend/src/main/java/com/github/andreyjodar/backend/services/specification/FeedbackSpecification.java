package com.github.andreyjodar.backend.services.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.andreyjodar.backend.models.dtos.filter.FeedbackFilterDTO;
import com.github.andreyjodar.backend.models.entities.Feedback;

public class FeedbackSpecification {
    public static Specification<Feedback> withGrade(Integer grade) {
        if(grade == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("grade"), grade);
    } 

    public static Specification<Feedback> withAuctionId(Long auctionId) {
        if (auctionId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("auction").get("id"), auctionId);
    }

    public static Specification<Feedback> withAuthorId(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Feedback> buildFilter(FeedbackFilterDTO feedbackFilterDTO) {
        Specification<Feedback> spec = Specification.where(null);
        if(feedbackFilterDTO.getGrade() != null) {
            spec = spec.and(withGrade(feedbackFilterDTO.getGrade()));
        }
        if(feedbackFilterDTO.getAuctionId() != null) {
            spec = spec.and(withAuctionId(feedbackFilterDTO.getAuctionId()));
        }
        if(feedbackFilterDTO.getAuthorId() != null) {
            spec = spec.and(withAuthorId(feedbackFilterDTO.getAuthorId()));
        }
        return spec;
    }
}
