package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.CategoryDto;
import ru.hackaton.backend.models.domain.Category;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface CategoryMapper {

    @Named("toCategoryDto")
    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryDto categoryDto);

    @IterableMapping(qualifiedByName = "toCategoryDto")
    Set<CategoryDto> mapToSet(Collection<Category> categories);

    @IterableMapping(qualifiedByName = "toCategoryDto")
    List<CategoryDto> mapToList(Collection<Category> categories);

}