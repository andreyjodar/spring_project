// package com.github.andreyjodar.backend.features.auction.service;

// import java.time.LocalDateTime;

// import org.springframework.data.jpa.domain.Specification;

// import com.github.andreyjodar.backend.features.auction.model.Auction;
// import com.github.andreyjodar.backend.features.auction.model.AuctionFilterRequest;
// import com.github.andreyjodar.backend.features.auction.model.AuctionStatus;

// public class AuctionSpecifications {

//     public static Specification<Auction> withTitle(String title) {
//         if (title == null || title.isBlank()) {
//             return null; 
//         }
//         return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
//     }

//     public static Specification<Auction> withCategory(Long categoryId) {
//         if (categoryId == null) {
//             return null;
//         }
//         return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
//     }
    
//     public static Specification<Auction> withStatus(AuctionStatus status) {
//         if (status == null) {
//             return null;
//         }
//         return (root, query, cb) -> cb.equal(root.get("status"), status);
//     }

//     public static Specification<Auction> withStartDateBetween(LocalDateTime min, LocalDateTime max) {
//         if (min == null && max == null) {
//             return null;
//         }
//         return (root, query, cb) -> {
//             if (min != null && max != null) {
//                 return cb.between(root.get("startDateTime"), min, max);
//             } else if (min != null) {
//                 return cb.greaterThanOrEqualTo(root.get("startDateTime"), min);
//             } else { 
//                 return cb.lessThanOrEqualTo(root.get("startDateTime"), max);
//             }
//         };
//     }

//     public static Specification<Auction> buildFilter(AuctionFilterRequest filter) {
//         Specification<Auction> spec = Specification.where(null); 
        
//         if (filter.getTitle() != null) {
//             spec = spec.and(withTitle(filter.getTitle()));
//         }
//         if (filter.getCategoryId() != null) {
//             spec = spec.and(withCategory(filter.getCategoryId()));
//         }
//         if (filter.getStatus() != null) {
//             spec = spec.and(withStatus(filter.getStatus()));
//         }
//         if (filter.getStartDateMin() != null || filter.getStartDateMax() != null) {
//             spec = spec.and(withStartDateBetween(filter.getStartDateMin(), filter.getStartDateMax()));
//         }

//         spec = spec.and((root, query, cb) -> cb.isFalse(root.get("deleted")));
//         return spec;
//     }
// }
