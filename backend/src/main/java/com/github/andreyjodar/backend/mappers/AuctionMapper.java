package com.github.andreyjodar.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.andreyjodar.backend.models.enums.AuctionStatus;
import com.github.andreyjodar.backend.services.interfaces.CategoryService;
import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.request.AuctionCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.AuctionUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Category;

@Mapper(componentModel = "spring", imports = { com.github.andreyjodar.backend.models.enums.AuctionStatus.class })
public abstract class AuctionMapper {
    
    @Autowired
    protected AuthUserProvider authUserProvider;

    @Autowired
    protected CategoryService categoryService;
    
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "expandedDescription", source = "expandedDescription")    
    @Mapping(target = "auctioneer", expression = "java(authUserProvider.getAuthUser())")
    @Mapping(target = "category", expression = "java(categoryService.findById(dto.getCategoryId()))")
    @Mapping(target = "startDateTime", source = "startDateTime")
    @Mapping(target = "endDateTime", source = "endDateTime")
    @Mapping(target = "minBid", source = "minBid")
    @Mapping(target = "incrementValue", expression = "java(0.0F)")
    @Mapping(target = "status", expression = "java(AuctionStatus.ACTIVE)")
    public abstract Auction toEntity(AuctionCreationDTO dto);

    @Mapping(target = "title", source = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "expandedDescription", source = "expandedDescription", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "startDateTime", source = "startDateTime", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "endDateTime", source = "endDateTime", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "minBid", source = "minBid", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", expression = "java(updateCategory(dto.getCategoryId(), auction.getCategory()))")
    public abstract void updateEntityFromDTO(AuctionUpdateDTO dto, @MappingTarget Auction auction);

    protected Category updateCategory(Long newCategoryId, Category existingCategory) {
        if (newCategoryId == null) {
            return existingCategory;
        }
        return categoryService.findById(newCategoryId); 
    }
}
