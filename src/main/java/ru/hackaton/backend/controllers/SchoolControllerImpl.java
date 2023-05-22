package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.services.SchoolService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class SchoolControllerImpl implements SchoolController {

    private final SchoolService schoolService;

    @Override
    public SchoolDto create(SchoolDto schoolDto) {
        return schoolService.createSchool(schoolDto);
    }

    @Override
    public SchoolDto read(long id) {
        return schoolService.getSchoolById(id);
    }

    @Override
    public void update(long id, SchoolDto schoolDto) {
        schoolService.updateSchool(id, schoolDto);
    }

    @Override
    public void delete(long id) {
        schoolService.deleteSchoolById(id);
    }

    @Override
    public PageWrapper<SchoolDto> readAll(Integer pageNum, Integer perPage) {
        return schoolService.getAllSchools(pageNum, perPage);
    }

}
