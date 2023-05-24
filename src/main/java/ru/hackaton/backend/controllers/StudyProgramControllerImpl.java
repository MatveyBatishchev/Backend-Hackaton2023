package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.StudyProgramDto;
import ru.hackaton.backend.services.StudyProgramService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class StudyProgramControllerImpl implements StudyProgramController {

    private final StudyProgramService studyProgramService;

    @Override
    public StudyProgramDto create(StudyProgramDto studyProgramDto) {
        return studyProgramService.createStudyProgram(studyProgramDto);
    }

    @Override
    public StudyProgramDto read(long id) {
        return studyProgramService.getStudyProgramById(id);
    }

    @Override
    public void update(long id, StudyProgramDto studyProgramDto) {
        studyProgramService.updateStudyProgram(id, studyProgramDto);
    }

    @Override
    public void delete(long id) {
        studyProgramService.deleteStudyProgramById(id);
    }

    @Override
    public PageWrapper<StudyProgramDto> readAll(Integer pageNum, Integer perPage) {
        return studyProgramService.getAllStudyPrograms(pageNum, perPage);
    }

}