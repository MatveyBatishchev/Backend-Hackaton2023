package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.StudyProgramDto;
import ru.hackaton.backend.models.domain.StudyProgram;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface StudyProgramMapper {

    @Named("toStudyProgramDto")
    StudyProgramDto toDto(StudyProgram studyDto);

    @Mapping(target = "id", ignore = true)
    StudyProgram toStudyProgram(StudyProgramDto studyDtoDto);

    @Named("mapToStudyProgramDtoSet")
    @IterableMapping(qualifiedByName = "toStudyProgramDto")
    Set<StudyProgramDto> mapToSet(Collection<StudyProgram> studyDtos);

    @IterableMapping(qualifiedByName = "toStudyProgramDto")
    List<StudyProgramDto> mapToList(Collection<StudyProgram> studyDtos);

}
