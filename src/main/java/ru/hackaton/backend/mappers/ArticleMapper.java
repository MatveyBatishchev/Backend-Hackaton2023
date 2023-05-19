package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.models.domain.Article;
import ru.hackaton.backend.models.domain.ArticleContent;
import ru.hackaton.backend.repositories.ArticleTypeRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {ArticleTypeMapper.class, ArticleTypeRepository.class},
        imports = {LocalDateTime.class, ArticleContent.class})
public interface ArticleMapper {

    @Mapping(target = "content", expression = "java(article.getArticleContent().getContent())")
    @Mapping(target = "articleType", qualifiedByName = "toArticleTypeDto")
    ArticleDto toDto(Article article);

    @Named("toArticleDtoIgnoringContent")
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "articleType", qualifiedByName = "toArticleTypeDto")
    ArticleDto toDtoIgnoringContent(Article article);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "articleContent", expression = "java(new ArticleContent(articleDto.getId(), article, articleDto.getContent()))")
    @Mapping(target = "articleType", source = "articleType.id", qualifiedByName = "getArticleTypeReferenceById")
    Article toArticle(ArticleDto articleDto);

    @IterableMapping(qualifiedByName = "toArticleDtoIgnoringContent")
    List<ArticleDto> mapToList(Collection<Article> articles);

}