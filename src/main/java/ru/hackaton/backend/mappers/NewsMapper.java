package ru.hackaton.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.models.domain.News;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {CategoryMapper.class},
        imports = {LocalDateTime.class})
public interface NewsMapper {

    @Mapping(target = "categoryDtos", source="categories")
    NewsDto toDto(News news);

    @Mapping(target = "id", ignore = true) // id will generate automatically
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    News toNews(NewsDto newsDto);

}

