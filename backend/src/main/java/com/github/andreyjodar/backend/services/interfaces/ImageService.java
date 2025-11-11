package com.github.andreyjodar.backend.services.interfaces;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Image;
import com.github.andreyjodar.backend.models.entities.User;

public interface ImageService {
    Image saveImage(Long auctionId, MultipartFile file) throws IOException;
    void deleteImage(Long id) throws IOException; 
    byte[] loadImage(String uniqueName) throws IOException;
    String getUploadDirectory();
    List<Image> findImagesByAuction(Long auctionId);
    void validateOperation(User authUser, Auction auction);
    Image findById(Long id);
}
