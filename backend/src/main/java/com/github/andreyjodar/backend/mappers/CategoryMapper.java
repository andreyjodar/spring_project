package com.github.andreyjodar.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.github.andreyjodar.backend.models.dtos.request.CategoryCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.CategoryUpdateDTO;
import com.github.andreyjodar.backend.models.entities.Category;

@Mapper(componentModel = "spring") 
public abstract class CategoryMapper {

    @Mapping(target = "name", expression = "java(dto.getName())")
    @Mapping(target = "note", expression = "java(dto.getNote())")
    public abstract Category toEntity(CategoryCreationDTO dto);

    @Mapping(target = "name", expression = "java(dto.getName())")
    @Mapping(target = "note", expression = "java(dto.getNote())")
    public abstract void updateEntityFromDto(CategoryUpdateDTO dto, @MappingTarget Category category);
}
