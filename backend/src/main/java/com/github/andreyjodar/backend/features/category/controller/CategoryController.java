package com.github.andreyjodar.backend.features.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.model.CategoryRequest;
import com.github.andreyjodar.backend.features.category.model.CategoryResponse;
import com.github.andreyjodar.backend.features.category.service.CategoryService;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User authUser) {
        Category category = categoryService.fromDto(categoryRequest);
        Category categoryDb = categoryService.create(category, authUser);
        return ResponseEntity.ok(categoryService.toDto(categoryDb));
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> update(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.fromDto(categoryRequest);
        Category categoryDb = categoryService.update(category);
        return ResponseEntity.ok(categoryService.toDto(categoryDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Categoria Excluída");
    }

}
