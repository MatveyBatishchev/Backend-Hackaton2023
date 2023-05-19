package ru.hackaton.backend.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.hackaton.backend.dtos.AnswerDto;
import ru.hackaton.backend.models.domain.Answer;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class)
public interface AnswerMapper {

    AnswerDto toDto(Answer answer);

    @InheritInverseConfiguration
    Answer toAnswer(AnswerDto answerDto);
}
