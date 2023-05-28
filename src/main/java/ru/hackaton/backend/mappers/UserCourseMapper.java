package ru.hackaton.backend.mappers;


import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hackaton.backend.dtos.UserCourseDto;
import ru.hackaton.backend.models.domain.UserCourse;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {UserMapper.class, CourseMapper.class, UserCourseLessonMapper.class})
public interface UserCourseMapper {

    @Mapping(source = "user", target = "user", qualifiedByName = "toId")
    @Mapping(source = "course", target = "course")
    @Mapping(source = "completion", target = "completion")
    @Mapping(source = "completedLessons", target = "completedLessons", qualifiedByName = "toUserCourseLessonList")
    UserCourseDto toDto(UserCourse userCourse);

}
