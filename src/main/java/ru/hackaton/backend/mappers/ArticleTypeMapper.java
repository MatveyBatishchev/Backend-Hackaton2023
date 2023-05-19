package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.ArticleTypeDto;
import ru.hackaton.backend.models.domain.ArticleType;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface ArticleTypeMapper {

    @Named("toArticleTypeDto")
    ArticleTypeDto toDto(ArticleType articleType);

    @Mapping(target = "id", ignore = true)
    ArticleType toArticleType(ArticleTypeDto articleTypeDto);

    @IterableMapping(qualifiedByName = "toArticleTypeDto")
    List<ArticleTypeDto> mapToList(Collection<ArticleType> articleTypes);

}