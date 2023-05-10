package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.models.domain.News;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {CategoryMapper.class},
        imports = {LocalDateTime.class})
public interface NewsMapper {

    @Mapping(target = "categoryDtos", source = "categories")
    NewsDto toDto(News news);

    @Mapping(target = "id", ignore = true) // id will generate automatically
    News toNews(NewsDto newsDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    void updateNewsFromDto(NewsDto newsDto, @MappingTarget News entity);

}

