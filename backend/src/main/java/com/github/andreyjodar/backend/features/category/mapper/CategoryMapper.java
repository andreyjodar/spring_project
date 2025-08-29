package com.github.andreyjodar.backend.features.category.mapper;

import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.model.CategoryRequest;

@Component
public class CategoryMapper {
    
    public Category fromDto(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setNote(categoryRequest.getNote());
        return category;
    }
}
