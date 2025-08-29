package com.github.andreyjodar.backend.features.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.category.model.CategoryRequest;
import com.github.andreyjodar.backend.features.category.service.CategoryService;
import com.github.andreyjodar.backend.features.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthUserProvider authUserProvider;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(categoryService.createCategory(authUser, categoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        User authUser = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(categoryService.updateCategory(id, authUser, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        User authUser = authUserProvider.getAutheticatedUser();
        categoryService.deleteCategory(id, authUser);
        return ResponseEntity.ok("Category was Deleted Successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<Category>> getMyCategories(Pageable pageable) {
        User user = authUserProvider.getAutheticatedUser();
        return ResponseEntity.ok(categoryService.findByUser(user, pageable));
    }
}
