package com.github.andreyjodar.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByAuctionId(Long auctionId);
}
