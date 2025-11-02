package com.github.andreyjodar.backend.services.specification;

import org.springframework.data.jpa.domain.Specification;

import com.github.andreyjodar.backend.models.dtos.filter.CategoryFilterDTO;
import com.github.andreyjodar.backend.models.entities.Category;

public class CategorySpecification {
    public static Specification<Category> nameLike(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; 
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Category> noteLike(String note) {
        if (note == null || note.trim().isEmpty()) {
            return null; 
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("note")), "%" + note.toLowerCase() + "%");
    }

    public static Specification<Category> buildFilter(CategoryFilterDTO categoryFilterDTO) {
        Specification<Category> spec = Specification.where(null);

        if (categoryFilterDTO.getName() != null && !categoryFilterDTO.getName().trim().isEmpty()) {
            spec = spec.and(CategorySpecification.nameLike(categoryFilterDTO.getName()));
        }

        if (categoryFilterDTO.getNote() != null && !categoryFilterDTO.getNote().trim().isEmpty()) {
            spec = spec.and(CategorySpecification.nameLike(categoryFilterDTO.getNote()));
        }

        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted")));
        return spec;
    }
}
