package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.StudyProgramDto;
import ru.hackaton.backend.mappers.StudyProgramMapper;
import ru.hackaton.backend.models.domain.StudyProgram;
import ru.hackaton.backend.repositories.StudyProgramRepository;
import ru.hackaton.backend.util.PageWrapper;

@Service
@RequiredArgsConstructor
public class StudyProgramService {

    private final StudyProgramRepository studyProgramRepository;

    private final StudyProgramMapper studyProgramMapper;

    private StudyProgram findStudyProgramById(long id) {
        return studyProgramRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Округ с id " + id + " не был найден!"));
    }

    public StudyProgramDto createStudyProgram(StudyProgramDto studyProgramDto) {
        StudyProgram newStudyProgram = studyProgramRepository.save(studyProgramMapper.toStudyProgram(studyProgramDto));
        return studyProgramMapper.toDto(newStudyProgram);
    }

    public StudyProgramDto getStudyProgramById(long id) {
        return studyProgramMapper.toDto(findStudyProgramById(id));
    }

    public void updateStudyProgram(long id, StudyProgramDto studyProgramDto) {
        StudyProgram studyProgram = studyProgramMapper.toStudyProgram(studyProgramDto);
        studyProgram.setId(id);
        studyProgramRepository.save(studyProgram);
    }

    public void deleteStudyProgramById(long id) {
        studyProgramRepository.deleteById(id);
    }

    public PageWrapper<StudyProgramDto> getAllStudyPrograms(Integer pageNum, Integer perPage) {
        Page<StudyProgram> page = studyProgramRepository.findAll(PageRequest.of(pageNum, perPage));
        return new PageWrapper<>(page.getTotalElements(), studyProgramMapper.mapToList(page.getContent()));
    }

}
