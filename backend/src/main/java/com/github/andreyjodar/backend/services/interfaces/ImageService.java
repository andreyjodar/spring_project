package com.github.andreyjodar.backend.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.andreyjodar.backend.models.entities.Image;

public interface ImageService {
    Image saveImage(Long auctionId, MultipartFile file) throws IOException;
    void deleteImage(Long id) throws IOException; 
    List<Image> findImagesByAuction(Long auctionId);
    Image findById(Long id);
}
