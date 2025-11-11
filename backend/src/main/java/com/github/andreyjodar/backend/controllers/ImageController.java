package com.github.andreyjodar.backend.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Image;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;
import com.github.andreyjodar.backend.services.interfaces.ImageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auctions/{auctionId}/images")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final AuctionService auctionService;
    private final AuthUserProvider authUserProvider;
    private final MessageSource messageSource;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
    public ResponseEntity<List<Image>> upload(@PathVariable("auctionId") Long auctionId, @RequestPart("files") List<MultipartFile> files) throws IOException {
        User authUser =  authUserProvider.getAuthUser();
        Auction auction = auctionService.findById(auctionId);

        auctionService.validateUpdate(authUser, auction);
        imageService.validateOperation(authUser, auction);

        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile file: files) {
            savedImages.add(imageService.saveImage(auctionId, file));
        }
        return ResponseEntity.ok(savedImages);
    }

    @DeleteMapping("{imageId}")
    @PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponseDTO> delete(@PathVariable("auctionId") Long auctionId, @PathVariable("imageId") Long imageId) throws IOException {
        User authUser =  authUserProvider.getAuthUser();
        Auction auction = auctionService.findById(auctionId);

        auctionService.validateUpdate(authUser, auction);
        imageService.validateOperation(authUser, auction);
        imageService.deleteImage(imageId);

        return ResponseEntity.ok(new SimpleResponseDTO(messageSource.getMessage("success.auctions.deleted",
            new Object[] { imageId }, LocaleContextHolder.getLocale())));
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImageByAuctionId(@PathVariable("auctionId") Long auctionId) {
        return ResponseEntity.ok(imageService.findImagesByAuction(auctionId));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Image> getImageById(@PathVariable("imageId") Long imageId) {
        return ResponseEntity.ok(imageService.findById(imageId));
    }

    @GetMapping("/file/{uniqueName}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String uniqueName) throws IOException {
        byte[] imageBytes = imageService.loadImage(uniqueName);
        
        String mimeType = Files.probeContentType(Paths.get(imageService.getUploadDirectory())
            .resolve(uniqueName));
        if (mimeType == null) {
            mimeType = MediaType.IMAGE_JPEG_VALUE; 
        }
        
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mimeType))
            .contentLength(imageBytes.length)
            .body(imageBytes);
    }
        
}
