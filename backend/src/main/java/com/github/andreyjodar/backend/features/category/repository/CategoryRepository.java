package com.github.andreyjodar.backend.features.category.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.category.model.Category;
import com.github.andreyjodar.backend.features.user.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);

    public Page<Category> findByAuthor(User author, Pageable pageable);
} 