package com.github.andreyjodar.backend.services.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.github.andreyjodar.backend.models.dtos.filter.AuctionFilterDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.enums.AuctionStatus;

public class AuctionSpecification {
    public static Specification<Auction> titleLike(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null; 
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Auction> inDateRange(LocalDateTime startDateFilter, LocalDateTime endDateFilter) {
        if (startDateFilter == null && endDateFilter == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> {
            Specification<Auction> startSpec = null;
            Specification<Auction> endSpec = null;

            if (startDateFilter != null) {
                startSpec = (r, q, cb) -> cb.greaterThanOrEqualTo(r.get("startDateTime"), startDateFilter);
            }

            if (endDateFilter != null) {
                endSpec = (r, q, cb) -> cb.lessThanOrEqualTo(r.get("endDateTime"), endDateFilter);
            }
            
            if (startSpec != null && endSpec != null) {
                return criteriaBuilder.and(
                    startSpec.toPredicate(root, query, criteriaBuilder),                        
                    endSpec.toPredicate(root, query, criteriaBuilder)
                );
            } else if (startSpec != null) {
                return startSpec.toPredicate(root, query, criteriaBuilder);
            } else {
                return endSpec.toPredicate(root, query, criteriaBuilder);
            }
        };
    }

    public static Specification<Auction> withCategoryId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Auction> withStatus(AuctionStatus status) {
        if (status == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Auction> buildFilter(AuctionFilterDTO auctionFilterDTO) {
        Specification<Auction> spec = Specification.unrestricted();

        if (auctionFilterDTO.getTitle() != null) {
            spec = spec.and(AuctionSpecification.titleLike(auctionFilterDTO.getTitle()));
        }

        if (auctionFilterDTO.getStatus() != null) {
            spec = spec.and(AuctionSpecification.withStatus(auctionFilterDTO.getStatus()));
        }

        if(auctionFilterDTO.getStartDateTime() != null || auctionFilterDTO.getEndDateTime() != null) {
            spec = spec.and(AuctionSpecification.inDateRange(auctionFilterDTO.getStartDateTime(), auctionFilterDTO.getEndDateTime()));
        }

        if(auctionFilterDTO.getCategoryId() != null) {
            spec = spec.and(AuctionSpecification.withCategoryId(auctionFilterDTO.getCategoryId()));
        }

        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted")));
        return spec;
    }
}
