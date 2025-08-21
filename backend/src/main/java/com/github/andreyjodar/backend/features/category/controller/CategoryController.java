package com.github.andreyjodar.backend.features.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        category.setAuthor(authUser);
        Category categoryDb = categoryService.create(category);
        return ResponseEntity.ok(categoryService.toDto(categoryDb));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.fromDto(categoryRequest);
        category.setId(id);
        Category categoryDb = categoryService.update(category);
        return ResponseEntity.ok(categoryService.toDto(categoryDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("Categoria Excluída");
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<Category> categoriesDb = categoryService.findAll();
        List<CategoryResponse> categoriesResponse = categoriesDb.stream().map(categoryService::toDto).toList();
        return ResponseEntity.ok(categoriesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable("id") Long id) {
        Category categoryDb = categoryService.findById(id);
        return ResponseEntity.ok(categoryService.toDto(categoryDb));
    }

}
