package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.CourseDto;
import ru.hackaton.backend.services.CourseService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {

    private final CourseService courseService;


    @Override
    public CourseDto create(CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @Override
    public CourseDto read(long id) {
        return courseService.getCourseById(id);
    }

    @Override
    public void update(long id, CourseDto courseDto) {
        courseService.updateCourse(id, courseDto);
    }

    @Override
    public void delete(long id) {
        courseService.deleteCourse(id);
    }

    @Override
    public PageWrapper<CourseDto> readAll(Integer pageNum, Integer perPage) {
        //TODO add filter by artId. change artId from null
        return courseService.getAllCourses(pageNum, perPage, null);
    }
}
