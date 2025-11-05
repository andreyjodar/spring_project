package com.github.andreyjodar.backend.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.models.dtos.filter.CategoryFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.services.interfaces.CategoryService;
import com.github.andreyjodar.backend.models.entities.Category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getFiltered(CategoryFilterDTO categoryFilterDTO, Pageable pageable) {
        return ResponseEntity.ok(categoryService.findFiltered(categoryFilterDTO, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryCreationDTO categoryCreationDTO) {
        return ResponseEntity.ok(categoryService.create(categoryCreationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        return ResponseEntity.ok(categoryService.update(id, categoryUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponseDTO> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.categories.deleted",
            new Object[] { id }, LocaleContextHolder.getLocale())));
    }
}
