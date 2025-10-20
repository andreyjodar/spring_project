// package com.github.andreyjodar.backend.features.category.service;

// import org.springframework.data.jpa.domain.Specification;

// import com.github.andreyjodar.backend.features.category.model.Category;
// import com.github.andreyjodar.backend.features.category.model.CategoryFilterRequest;

// public class CategorySpecifications {
    
//     public static Specification<Category> withName(String name) {
//         if(name == null || name.isBlank()) {
//             return null;
//         }
//         return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
//     }

//     public static Specification<Category> buildFilter(CategoryFilterRequest filter) {
//         Specification<Category> spec = Specification.where(null); 
        
//         if (filter.getName() != null) {
//             spec = spec.and(withName(filter.getName()));
//         }

//         spec = spec.and((root, query, cb) -> cb.isFalse(root.get("deleted")));
//         return spec;
//     }
// }
