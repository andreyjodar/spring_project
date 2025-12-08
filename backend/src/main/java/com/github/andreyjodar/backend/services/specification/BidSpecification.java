package com.github.andreyjodar.backend.services.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.andreyjodar.backend.models.dtos.filter.BidFilterDTO;
import com.github.andreyjodar.backend.models.entities.Bid;

import java.time.LocalDateTime;

public class BidSpecification {
    public static Specification<Bid> priceBetween(Double minPrice, Double maxPrice) {
        if(minPrice == null && maxPrice == null) {
            return null; 
        }

        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("bidPrice"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("bidPrice"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("bidPrice"), maxPrice);
            }
        };
    }

    public static Specification<Bid> dateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime) {
        if(minDateTime == null && maxDateTime == null) {
            return null; 
        }

        return (root, query, criteriaBuilder) -> {
            if (minDateTime != null && maxDateTime != null) {
                return criteriaBuilder.between(root.get("createdAt"), minDateTime, maxDateTime);
            } else if (minDateTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), minDateTime);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), maxDateTime);
            }
        };
    }

    public static Specification<Bid> withAuctionId(Long auctionId) {
        if(auctionId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("auction").get("id"), auctionId);
    }

    public static Specification<Bid> withBidderId(Long bidderId) {
        if(bidderId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("bidder").get("id"), bidderId);
    }

    public static Specification<Bid> buildFilter(BidFilterDTO bidFilterDTO) {
        Specification<Bid> spec = Specification.unrestricted();
        
        Specification<Bid> auctionSpec = withAuctionId(bidFilterDTO.getAuctionId());
        if (auctionSpec != null) {
            spec = spec.and(auctionSpec);
        }
        Specification<Bid> bidderSpec = withBidderId(bidFilterDTO.getBidderId());
        if (bidderSpec != null) {
            spec = spec.and(bidderSpec);
        }
        Specification<Bid> priceSpec = priceBetween(bidFilterDTO.getMinPrice(), bidFilterDTO.getMaxPrice());
        if (priceSpec != null) {
            spec = spec.and(priceSpec);
        }
        Specification<Bid> dateSpec = dateTimeBetween(bidFilterDTO.getMinDateTime(), bidFilterDTO.getMaxDateTime());
        if (dateSpec != null) {
            spec = spec.and(dateSpec);
        }
        return spec;
    }
}