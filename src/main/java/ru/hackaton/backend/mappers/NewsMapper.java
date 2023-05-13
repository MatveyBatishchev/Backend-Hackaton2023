package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.models.domain.News;
import ru.hackaton.backend.models.domain.NewsContent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {CategoryMapper.class},
        imports = {LocalDateTime.class, NewsContent.class})
public interface NewsMapper {

    @Mapping(target = "content", expression = "java(news.getNewsContent().getContent())")
    NewsDto toDto(News news);

    @Named("toNewsDtoIgnoringContent")
    @Mapping(target = "content", ignore = true)
    NewsDto toDtoIgnoringContent(News news);

    @Named("toNewsDtoIgnoringContentAndCategories")
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "categories", ignore = true)
    NewsDto toDtoIgnoringContentAndCategories(News news);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "newsContent", expression = "java(new NewsContent(newsDto.getId(), news, newsDto.getContent()))")
    News toNews(NewsDto newsDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "newsContent", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateNewsFromDto(NewsDto newsDto, @MappingTarget News entity);

    @IterableMapping(qualifiedByName = "toNewsDtoIgnoringContent")
    List<NewsDto> mapToList(Collection<News> news);

    @IterableMapping(qualifiedByName = "toNewsDtoIgnoringContentAndCategories")
    List<NewsDto> mapToListIgnoringCategories(Collection<News> news);

}