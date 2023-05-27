package ru.hackaton.backend.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.LessonDto;
import ru.hackaton.backend.models.domain.Lesson;
import ru.hackaton.backend.models.domain.LessonContent;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        imports = {LocalDateTime.class, LessonContent.class})
public interface LessonMapper {

    @Mapping(target = "content", expression = "java(lesson.getContent().getContent())")
    LessonDto toLessonDto(Lesson lesson);

    @Named("toLessonDtoWithoutContent")
    @Mapping(target="content", ignore = true)
    LessonDto toLessonDtoWithoutContent(Lesson lesson);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "content", expression = "java(new LessonContent(lessonDto.getId(), lesson, lessonDto.getContent()))")
    Lesson toLesson(LessonDto lessonDto);



//    @IterableMapping()
//    List<CourseDto> toDtoList(Collection<Course> courseList);
//
//    @IterableMapping(elementTargetType = Course.class)
//    List<Course> toCourseList(Collection<CourseDto> courseDtoList);

}
