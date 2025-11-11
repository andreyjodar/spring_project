package com.github.andreyjodar.backend.services.implement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Image;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.ImageRepository;
import com.github.andreyjodar.backend.services.interfaces.ImageService;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Value("${file.upload-dir}")
    String uploadDirectory;
    private final ImageRepository imageRepository;
    private final AuctionRepository auctionRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public Image saveImage(Long auctionId, MultipartFile file) throws IOException {
        validateFile(file);
        Auction auction = validateAuction(auctionId);
        String originName = file.getOriginalFilename();
        String uniqueName = uniqueNameGenerator(originName);
        saveLocalImage(uniqueName, file); 

        Image image = new Image(uniqueName, originName, auction);

        try {
            return imageRepository.save(image);
        } catch (Exception e) {
            deleteLocalImage(uniqueName);
            throw e; 
        }
    }

    @Override
    public byte[] loadImage(String uniqueName) throws IOException {
        Path filePath = Paths.get(uploadDirectory).resolve(uniqueName);
        if (!Files.exists(filePath)) {
            throw new NotFoundException(messageSource.getMessage("exception.images.filenotfound",
                new Object[] { uniqueName }, LocaleContextHolder.getLocale()));
        }
        return Files.readAllBytes(filePath);
    }

    @Override
    public String getUploadDirectory() {
        return uploadDirectory;
    }


    @Override
    @Transactional
    public void deleteImage(Long id) throws IOException {
        Image image = findById(id);
        deleteLocalImage(image.getUniqueName()); 
        imageRepository.delete(image);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Image> findImagesByAuction(Long auctionId) {
        return imageRepository.findAllByAuctionId(auctionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Image findById(Long id) {
        return imageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.images.metanotfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    private Auction validateAuction(Long auctionId) {
        if(auctionRepository.findById(auctionId).isEmpty()) {
            throw new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
                new Object[] { auctionId }, LocaleContextHolder.getLocale()));       
        } else {
            return auctionRepository.findById(auctionId).get();
        }
    }

    @Override
    public void validateOperation(User authUser, Auction auction) {
        if(!auction.getAuctioneer().getId().equals(authUser.getId())) {
            throw new NotFoundException(messageSource.getMessage("exception.auctions.notowner",
                new Object[] { auction.getId() }, LocaleContextHolder.getLocale()));  
        }
    }
    
    private void validateFile(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.images.emptyimage",
                null, LocaleContextHolder.getLocale()));  
        }
        if(file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.images.invalidtype",
                new Object[] { file.getContentType() }, LocaleContextHolder.getLocale()));  
        }
        if(file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(messageSource.getMessage("exception.images.largefile",
                new Object[] { file.getName() }, LocaleContextHolder.getLocale()));  
        }
    }

    private String uniqueNameGenerator(String originName) {
        String fileExtension = originName != null && originName.contains(".") 
            ? originName.substring(originName.lastIndexOf(".")) : "";
        return UUID.randomUUID().toString() + fileExtension;
    }

    private void saveLocalImage(String uniqueName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(uniqueName);
        Files.copy(file.getInputStream(), filePath);
    }

    private void deleteLocalImage(String uniqueName) throws IOException {
        Path filePath = Paths.get(uploadDirectory).resolve(uniqueName);
        File file = filePath.toFile();
        if (file.exists() && !file.isDirectory()) {
            Files.delete(filePath);
        }
    }

}
