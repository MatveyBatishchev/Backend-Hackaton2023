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

    NewsDto toDto(News news);

    // id will be generated automatically
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    News toNews(NewsDto newsDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "categories", ignore = true)
    void updateNewsFromDto(NewsDto newsDto, @MappingTarget News entity);

}

