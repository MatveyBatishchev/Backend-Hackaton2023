package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.AchievementDto;
import ru.hackaton.backend.models.domain.Achievement;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface AchievementMapper {

    @Named("toAchievementDto")
    AchievementDto toDto(Achievement achievement);

    @IterableMapping(qualifiedByName = "toAchievementDto")
    List<AchievementDto> mapToList(Collection<Achievement> achievements);

}
