package com.github.andreyjodar.backend.services.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.github.andreyjodar.backend.models.dtos.filter.PaymentFilterDTO;
import com.github.andreyjodar.backend.models.entities.Payment;

public class PaymentSpecification {
        public static Specification<Payment> priceBetween(Double minPrice, Double maxPrice) {
        if(minPrice == null && maxPrice == null) {
            return null; 
        }

        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    public static Specification<Payment> dateTimeBetween(LocalDateTime minDateTime, LocalDateTime maxDateTime) {
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

    public static Specification<Payment> withAuctionId(Long auctionId) {
        if(auctionId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("auction").get("id"), auctionId);
    }

    public static Specification<Payment> withBuyerId(Long buyerId) {
        if(buyerId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("buyer").get("id"), buyerId);
    }

    public static Specification<Payment> buildFilter(PaymentFilterDTO paymentFilterDTO) {
        Specification<Payment> spec = Specification.unrestricted();
        
        Specification<Payment> auctionSpec = withAuctionId(paymentFilterDTO.getAuctionId());
        if (auctionSpec != null) {
            spec = spec.and(auctionSpec);
        }
        Specification<Payment> buyerSpec = withAuctionId(paymentFilterDTO.getAuctionId());
        if (buyerSpec != null) {
            spec = spec.and(auctionSpec);
        }
        Specification<Payment> priceSpec = priceBetween(paymentFilterDTO.getMinPrice(), paymentFilterDTO.getMaxPrice());
        if (priceSpec != null) {
            spec = spec.and(priceSpec);
        }
        Specification<Payment> dateSpec = dateTimeBetween(paymentFilterDTO.getMinDateTime(), paymentFilterDTO.getMaxDateTime());
        if (dateSpec != null) {
            spec = spec.and(dateSpec);
        }
        return spec;
    }

}
