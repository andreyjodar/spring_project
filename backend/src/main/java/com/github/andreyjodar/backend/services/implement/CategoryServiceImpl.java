package com.github.andreyjodar.backend.services.implement;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.mappers.CategoryMapper;
import com.github.andreyjodar.backend.models.dtos.filter.CategoryFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Category;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.CategoryRepository;
import com.github.andreyjodar.backend.services.interfaces.CategoryService;
import com.github.andreyjodar.backend.services.specification.CategorySpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AuctionRepository auctionRepository;
    private final MessageSource messageSource;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.categories.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findFiltered(CategoryFilterDTO categoryFilterDTO, Pageable pageable) {
        Specification<Category> spec = CategorySpecification.buildFilter(categoryFilterDTO);
        return categoryRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Category create(CategoryCreationDTO categoryCreationDTO) {
        validateName(categoryCreationDTO.getName());
        Category categoryCreate = categoryMapper.toEntity(categoryCreationDTO);
        return categoryRepository.save(categoryCreate);
    }

    @Override
    @Transactional
    public Category update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category categoryUpdate = findById(id);
        validateName(categoryUpdateDTO.getName(), categoryUpdate);
        categoryMapper.updateEntityFromDto(categoryUpdateDTO, categoryUpdate);
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category categoryDelete = findById(id);
        validateHasAuction(categoryDelete.getId());
        categoryRepository.delete(categoryDelete);
    }
    
    private void validateName(String name) {
        if(categoryRepository.findByName(name).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.categories.existname",
                new Object[] { name }, LocaleContextHolder.getLocale())); 
        }
    }

    private void validateName(String name, Category category) {
        if(categoryRepository.findByName(name).isPresent() && !categoryRepository.findByName(name).get().getId().equals(category.getId())) {
            throw new BusinessException(messageSource.getMessage("exception.categories.existname",
                new Object[] { name }, LocaleContextHolder.getLocale())); 
        }
    }

    private void validateHasAuction(Long id) {
        if(auctionRepository.existsByCategoryId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.categories.hasauction",
                new Object[] { id }, LocaleContextHolder.getLocale())); 
        }
    }
}
