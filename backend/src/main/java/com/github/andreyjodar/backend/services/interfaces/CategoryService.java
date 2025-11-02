package com.github.andreyjodar.backend.services.interfaces;

import org.springframework.data.domain.Page;

import com.github.andreyjodar.backend.models.dtos.filter.CategoryFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Category;

public interface CategoryService {
    public Category findById(Long id);
    public Page<Category> findFiltered(CategoryFilterDTO categoryFilterDTO);
    public Category create(CategoryCreationDTO categoryCreationDTO);
    public Category update(CategoryUpdateDTO categoryUpdateDTO);
    public void delete(Long id);
}
