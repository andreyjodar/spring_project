package com.github.andreyjodar.backend.features.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.model.CategoryRequest;
import com.github.andreyjodar.backend.features.category.model.CategoryResponse;
import com.github.andreyjodar.backend.features.category.repository.CategoryRepository;
import com.github.andreyjodar.backend.features.user.model.User;
import com.github.andreyjodar.backend.features.user.service.UserService;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    public Category create(Category category, User authUser) {
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new BusinessException("Esta categoria já existe");
        }

        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        Category categoryDb = findById(category.getId());
        categoryDb.setName(category.getName());
        categoryDb.setNote(category.getNote());
        return categoryRepository.save(categoryDb);
    }

    public void delete(Long id) {
        Category categoryDb = findById(id);
        categoryRepository.delete(categoryDb);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage("user.notfound",
                        new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Category fromDto(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setNote(categoryRequest.getNote());
        return category;
    }

    public CategoryResponse toDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(category.getName());
        categoryResponse.setNote(category.getNote());
        categoryResponse.setUserResponse(userService.fromDto(category.getAuthor()));
        return categoryResponse;
    }
}
