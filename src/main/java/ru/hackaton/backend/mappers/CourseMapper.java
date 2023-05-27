package ru.hackaton.backend.mappers;

import org.mapstruct.*;
import ru.hackaton.backend.dtos.CourseDto;
import ru.hackaton.backend.models.domain.Course;
import ru.hackaton.backend.models.domain.CourseContent;
import ru.hackaton.backend.repositories.ArtRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        config = IgnoreUnmappedMapperConfig.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = {LessonMapper.class, ArtMapper.class, StudyProgramMapper.class, ArtRepository.class},
        imports = {LocalDateTime.class, CourseContent.class})
public interface CourseMapper {

    @Mapping(target = "content", expression = "java(course.getContent().getContent())")
    @Mapping(target = "art", qualifiedByName = "toArtDto")
    @Mapping(target = "studyProgram", qualifiedByName = "toStudyProgramDto")
    CourseDto toDto(Course course);

    @Mapping(source = "studyProgram", target = "studyProgram", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "content", expression = "java(new CourseContent(courseDto.getId(), course, courseDto.getContent()))")
    @Mapping(target = "art", source = "art.id", qualifiedByName = "getArtReferenceById")
    Course toCourse(CourseDto courseDto);


    @Named("mapWithoutLessons")
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "content", expression = "java(course.getContent().getContent())")
    CourseDto toDtoWithoutLessons(Course course);

    @IterableMapping(qualifiedByName = "mapWithoutLessons")
    List<CourseDto> toDtoList(Collection<Course> courseList);

    @IterableMapping(elementTargetType = Course.class)
    List<Course> toCourseList(Collection<CourseDto> courseDtoList);

}
