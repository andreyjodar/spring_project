package com.github.andreyjodar.backend.features.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exception.BusinessException;
import com.github.andreyjodar.backend.core.exception.NotFoundException;
import com.github.andreyjodar.backend.features.category.mapper.CategoryMapper;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.model.CategoryRequest;
import com.github.andreyjodar.backend.features.category.repository.CategoryRepository;
import com.github.andreyjodar.backend.features.user.model.User;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private CategoryMapper categoryMapper;

    public Category createCategory(User user, CategoryRequest categoryRequest) {
        if(!user.isSeller()) {
            throw new BusinessException(messageSource.getMessage("exception.categories.forbidden",
                new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
        }

        Category category = categoryMapper.fromDto(categoryRequest);
        category.setAuthor(user);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, User user, CategoryRequest categoryRequest) {        
        Category category = findById(id);
        if(!user.isSeller() || (user.isSeller() && !user.isAdmin() && !category.getAuthor().getId().equals(user.getId()))) {
            throw new BusinessException(messageSource.getMessage("exception.categories.forbidden",
                new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
        }

        category.setName(categoryRequest.getName());
        category.setNote(categoryRequest.getNote());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id, User user) {
        Category category = findById(id);
        if(!user.isSeller() || (user.isSeller() && !user.isAdmin() && !category.getAuthor().getId().equals(user.getId()))) {
            throw new BusinessException(messageSource.getMessage("exception.categories.forbidden",
                new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
        }

        categoryRepository.delete(category);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> findByUser(User user, Pageable pageable) {
        return categoryRepository.findByAuthor(user, pageable);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.categories.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }
}
