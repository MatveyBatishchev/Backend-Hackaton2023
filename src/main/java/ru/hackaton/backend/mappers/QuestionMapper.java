package ru.hackaton.backend.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.hackaton.backend.dtos.QuestionDto;
import ru.hackaton.backend.models.domain.Question;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        uses = {AnswerMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface QuestionMapper {

    QuestionDto toDto(Question question);

    @InheritInverseConfiguration
    Question toQuestion(QuestionDto questionDto);
}