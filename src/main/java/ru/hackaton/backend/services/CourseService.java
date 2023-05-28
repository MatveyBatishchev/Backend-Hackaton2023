package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.CourseDto;
import ru.hackaton.backend.mappers.CourseMapper;
import ru.hackaton.backend.models.domain.Course;
import ru.hackaton.backend.repositories.CourseRepository;
import ru.hackaton.backend.util.PageWrapper;

import static ru.hackaton.backend.repositories.CourseRepository.Specs.artIdEquals;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseDto getCourseById(long id) {
        return courseMapper.toDto(findCourseById(id));
    }

    public Course findCourseById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курс с id %d не был найден!".formatted(id)));

        return course;
    }

    public CourseDto createCourse(CourseDto courseDto) {
        Course courseToSave = courseMapper.toCourse(courseDto);
        Course savedCourse = courseRepository.save(courseToSave);

        return courseMapper.toDto(savedCourse);
    }

    public void updateCourse(long id, CourseDto courseDto) {
        Course updatedCourse = courseMapper.toCourse(courseDto);
        updatedCourse.setId(id);

        courseRepository.save(updatedCourse);

    }

    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }

    public PageWrapper<CourseDto> getAllCourses(int pageNum, int perPage, Long artId) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));

        Specification<Course> spec = Specification.where(null);
        if (artId != null) spec = spec.and(artIdEquals(artId));

        Page<Course> courses = courseRepository.findAll(spec, pageable);

        return new PageWrapper<>(courses.getTotalElements(), courseMapper.toDtoList(courses.getContent()));
    }
}
