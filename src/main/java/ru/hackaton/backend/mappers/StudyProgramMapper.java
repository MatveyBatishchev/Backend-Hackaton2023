package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.StudyProgramDto;
import ru.hackaton.backend.models.domain.StudyProgram;

import java.util.Collection;
import java.util.List;


@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface StudyProgramMapper {

    @Named("toStudyProgramDto")
    StudyProgramDto toDto(StudyProgram studyDto);

    @Mapping(target = "id", ignore = true)
    StudyProgram toStudyProgram(StudyProgramDto studyDtoDto);

    @IterableMapping(qualifiedByName = "toStudyProgramDto")
    List<StudyProgramDto> mapToList(Collection<StudyProgram> studyDtos);

}
