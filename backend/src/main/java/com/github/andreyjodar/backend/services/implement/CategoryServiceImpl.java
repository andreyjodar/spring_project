package com.github.andreyjodar.backend.services.implement;

import org.springframework.data.domain.Page;

import com.github.andreyjodar.backend.models.dtos.filter.CategoryFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Category;
import com.github.andreyjodar.backend.services.interfaces.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public Category findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Page<Category> findFiltered(CategoryFilterDTO categoryFilterDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findFiltered'");
    }

    @Override
    public Category create(CategoryCreationDTO categoryCreationDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Category update(CategoryUpdateDTO categoryUpdateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
