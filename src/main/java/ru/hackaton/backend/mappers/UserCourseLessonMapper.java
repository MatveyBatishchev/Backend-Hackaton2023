package ru.hackaton.backend.mappers;


import org.mapstruct.*;
import ru.hackaton.backend.dtos.UserCourseLessonDto;
import ru.hackaton.backend.models.domain.UserCourseLesson;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserCourseLessonMapper {


    @Named("toUserCourseLessonDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.lessonId", target = "id")
    UserCourseLessonDto toUserCourseLessonDto(UserCourseLesson userCourseLesson);

    @Named("toUserCourseLessonList")
    @IterableMapping(qualifiedByName = "toUserCourseLessonDto")
    List<UserCourseLessonDto> toUserCourseLessonList(Collection<UserCourseLesson> userCourseLessonList);
}
