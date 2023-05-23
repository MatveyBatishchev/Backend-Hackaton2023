package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.models.domain.School;
import ru.hackaton.backend.models.domain.SchoolContent;
import ru.hackaton.backend.repositories.DistrictRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {DistrictMapper.class, StudyProgramMapper.class, ArtMapper.class, DistrictRepository.class},
        imports = {LocalDateTime.class, SchoolContent.class})
public interface SchoolMapper {

    @Mapping(target = "content", expression = "java(school.getSchoolContent().getContent())")
    @Mapping(target = "district", qualifiedByName = "toDistrictDto")
    @Mapping(target = "studyPrograms", qualifiedByName = "mapToStudyProgramDtoSet")
    @Mapping(target = "arts", qualifiedByName = "mapToArtDtoSet")
    SchoolDto toDto(School school);

    @Named("toSchoolDtoIgnoringContentAndStudyPrograms")
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "studyPrograms", ignore = true)
    @Mapping(target = "district", qualifiedByName = "toDistrictDto")
    @Mapping(target = "arts", qualifiedByName = "mapToArtDtoSet")
    SchoolDto toDtoIgnoringContentAndStudyPrograms(School school);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "arts", ignore = true)
    @Mapping(target = "studyPrograms", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "schoolContent", expression = "java(new SchoolContent(schoolDto.getId(), school, schoolDto.getContent()))")
    @Mapping(target = "district", source = "district.id", qualifiedByName = "getDistrictReferenceById")
    School toSchool(SchoolDto schoolDto);

    @Mapping(target = "arts", ignore = true)
    @Mapping(target = "studyPrograms", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "schoolContent", expression = "java(new SchoolContent(schoolDto.getId(), entity, schoolDto.getContent()))")
    @Mapping(target = "district", source = "district.id", qualifiedByName = "getDistrictReferenceById")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateSchoolFromSchoolDto(SchoolDto schoolDto, @MappingTarget School entity);

    @IterableMapping(qualifiedByName = "toSchoolDtoIgnoringContentAndStudyPrograms")
    List<SchoolDto> mapToList(Collection<School> school);

}
