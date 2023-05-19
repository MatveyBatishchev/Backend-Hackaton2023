package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.TestDto;
import ru.hackaton.backend.models.domain.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {QuestionMapper.class, AnswerMapper.class},
        imports = {LocalDateTime.class})
public interface TestMapper {

    TestDto toDto(Test test);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Test toTest(TestDto testDto);


    @Named("mapWithoutQuestions")
    @Mapping(target = "questions", ignore = true)
    TestDto toDtoWithoutQuestions(Test test);

    @IterableMapping(qualifiedByName = "mapWithoutQuestions")
    List<TestDto> toDtoList(Collection<Test> testList);

    @IterableMapping(elementTargetType = Test.class)
    List<Test> toTestList(Collection<TestDto> testDtoList);

}
