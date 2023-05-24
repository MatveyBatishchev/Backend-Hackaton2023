package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.mappers.SchoolMapper;
import ru.hackaton.backend.models.domain.School;
import ru.hackaton.backend.repositories.SchoolRepository;
import ru.hackaton.backend.util.PageWrapper;

import java.util.List;

import static ru.hackaton.backend.repositories.SchoolRepository.Specs.*;


@Service
@RequiredArgsConstructor
public class SchoolService {

    private final static String DEFAULT_SORT_OPTION = "id";

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    private School findSchoolById(long id) {
        return schoolRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Учебное заведение с id " + id + " не была найдена!"));
    }

    public SchoolDto createSchool(SchoolDto schoolDto) {
        School newSchool = schoolMapper.toSchool(schoolDto);
        return schoolMapper.toDto(schoolRepository.save(newSchool));
    }

    public SchoolDto getSchoolById(long id) {
        return schoolMapper.toDto(findSchoolById(id));
    }

    public void updateSchool(long id, SchoolDto schoolDto) {
        School dbSchool = findSchoolById(id);
        schoolDto.setId(id);
        schoolMapper.updateSchoolFromSchoolDto(schoolDto, dbSchool);
        schoolRepository.save(dbSchool);
    }

    public void deleteSchoolById(long id) {
        schoolRepository.deleteById(id);
    }

    public PageWrapper<SchoolDto> getAllSchools(Integer pageNum, Integer perPage, String search, List<Long> districtIds, List<Long> artIds) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.ASC, DEFAULT_SORT_OPTION));

        Specification<School> spec = Specification.where(null);
        if (search != null) spec = spec.and(nameLike(search));
        if (districtIds != null) spec = spec.and(districtIdIn(districtIds));
        if (artIds != null) spec = spec.and(artsContainsAll(artIds));

        Page<School> page = schoolRepository.findAll(spec, pageable);
        return new PageWrapper<>(page.getTotalElements(), schoolMapper.mapToList(page.getContent()));
    }

    @Transactional
    public void updateSchoolArts(long id, Long[] artIds) {
        schoolRepository.deleteArtsFromSchool(id);
        schoolRepository.addArtsToSchool(id, artIds);
    }

    @Transactional
    public void updateSchoolStudyPrograms(long id, Long[] studyProgramIds) {
        schoolRepository.deleteStudyProgramsFromSchool(id);
        schoolRepository.addStudyProgramsToSchool(id, studyProgramIds);
    }

}