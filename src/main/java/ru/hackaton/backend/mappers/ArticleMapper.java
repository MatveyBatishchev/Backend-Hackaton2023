package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.models.domain.Article;
import ru.hackaton.backend.models.domain.ArticleContent;
import ru.hackaton.backend.repositories.ArticleTypeRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {ArticleTypeMapper.class, ArtMapper.class, ArticleTypeRepository.class},
        imports = {LocalDateTime.class, ArticleContent.class})
public interface ArticleMapper {

    @Mapping(target = "content", expression = "java(article.getArticleContent().getContent())")
    @Mapping(target = "articleType", qualifiedByName = "toArticleTypeDto")
    @Mapping(target = "arts", qualifiedByName = "mapToArtDtoSet")
    ArticleDto toDto(Article article);

    @Named("toArticleDtoIgnoringContentAndArts")
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "arts", ignore = true)
    @Mapping(target = "articleType", qualifiedByName = "toArticleTypeDto")
    ArticleDto toDtoIgnoringContentAndArts(Article article);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arts", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "articleContent", expression = "java(new ArticleContent(articleDto.getId(), article, articleDto.getContent()))")
    @Mapping(target = "articleType", source = "articleType.id", qualifiedByName = "getArticleTypeReferenceById")
    Article toArticle(ArticleDto articleDto);

    @Mapping(target = "arts", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "articleContent", expression = "java(new ArticleContent(articleDto.getId(), entity, articleDto.getContent()))")
    @Mapping(target = "articleType", source = "articleType.id", qualifiedByName = "getArticleTypeReferenceById")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateArticleFromArticleDto(ArticleDto articleDto, @MappingTarget Article entity);

    @IterableMapping(qualifiedByName = "toArticleDtoIgnoringContentAndArts")
    List<ArticleDto> mapToList(Collection<Article> articles);

}