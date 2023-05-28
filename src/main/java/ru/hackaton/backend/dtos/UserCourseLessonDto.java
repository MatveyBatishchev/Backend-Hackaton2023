package ru.hackaton.backend.dtos;


import lombok.Data;

@Data
public class UserCourseLessonDto {

    private Long id;

    private LessonDto lesson;

}
