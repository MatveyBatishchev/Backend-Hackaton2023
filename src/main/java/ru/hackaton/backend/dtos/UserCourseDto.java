package ru.hackaton.backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserCourseDto {

    private UserDto user;

    private CourseDto course;

    private Integer completion;

    private List<UserCourseLessonDto> completedLessons;

}
