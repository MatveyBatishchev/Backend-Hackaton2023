package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.ArtDto;
import ru.hackaton.backend.models.domain.Art;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface ArtMapper {

    @Named("toArtDto")
    ArtDto toDto(Art art);

    @Mapping(target = "id", ignore = true)
    Art toArt(ArtDto artDto);

    @Named("mapToArtDtoSet")
    @IterableMapping(qualifiedByName = "toArtDto")
    Set<ArtDto> mapToSet(Collection<Art> arts);

    @IterableMapping(qualifiedByName = "toArtDto")
    List<ArtDto> mapToList(Collection<Art> arts);

}
