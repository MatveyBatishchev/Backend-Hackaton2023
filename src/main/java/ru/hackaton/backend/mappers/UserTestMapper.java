package ru.hackaton.backend.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.models.domain.UserTest;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        imports = {LocalDateTime.class})
public interface UserTestMapper {


    UserTestDto toDto(UserTest userTest);

    @InheritInverseConfiguration
    @Mapping(target = "passedAt", expression = "java(LocalDateTime.now())")
    UserTest toUserTest(UserTestDto userTestDto);

}
