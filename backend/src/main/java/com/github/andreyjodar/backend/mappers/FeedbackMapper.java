package com.github.andreyjodar.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.FeedbackUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Feedback;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;

@Mapper(componentModel = "spring")
public abstract class FeedbackMapper {

    @Autowired
    protected AuctionService auctionService;
    
    @Autowired
    protected AuthUserProvider authUserProvider;
    
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "grade", source = "grade")
    @Mapping(target = "auction", expression = "java(auctionService.findById(dto.getAuctionId()))")
    @Mapping(target = "author", expression = "java(authUserProvider.getAuthUser())")
    public abstract Feedback toEntity(FeedbackCreationDTO dto);

    @Mapping(target = "comment", source = "comment", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "grade", source = "grade", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(Feedback feedback, @MappingTarget FeedbackUpdateDTO feedbackUpdateDTO);
}
