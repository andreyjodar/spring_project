package com.github.andreyjodar.backend.features.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    // public Category create(Category category) {

    // }

    // public Category update(Category category) {

    // }

    // public void delete(Long id) {

    // }
}
