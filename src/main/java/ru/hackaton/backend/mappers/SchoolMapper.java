package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.models.domain.School;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface SchoolMapper {


    SchoolDto toDto(School school);

    @Named("toSchoolDtoIgnoringContent")
    SchoolDto toDtoIgnoringContent(School school);

    @Mapping(target = "id", ignore = true)
    School toSchool(SchoolDto schoolDto);

    @IterableMapping(qualifiedByName = "toSchoolDtoIgnoringContent")
    List<SchoolDto> mapToList(Collection<School> schools);

}
