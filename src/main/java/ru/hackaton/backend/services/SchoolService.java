package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.mappers.SchoolMapper;
import ru.hackaton.backend.models.domain.School;
import ru.hackaton.backend.repositories.SchoolRepository;
import ru.hackaton.backend.util.PageWrapper;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class SchoolService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    private School findSchoolById(long id) {
        return schoolRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Статья с id " + id + " не была найдена!"));
    }

    public SchoolDto createSchool(SchoolDto schoolDto) {
        School newSchool = schoolMapper.toSchool(schoolDto);
        newSchool.setCreatedAt(LocalDateTime.now());
        return schoolMapper.toDto(schoolRepository.save(newSchool));
    }

    public SchoolDto getSchoolById(long id) {
        return schoolMapper.toDto(findSchoolById(id));
    }

    public void updateSchool(long id, SchoolDto schoolDto) {
        School updatedSchool = schoolMapper.toSchool(schoolDto);
        updatedSchool.setId(id);
        updatedSchool.getSchoolContent().setId(id);
        schoolRepository.save(updatedSchool);
    }

    public void deleteSchoolById(long id) {
        schoolRepository.deleteById(id);
    }

    public PageWrapper<SchoolDto> getAllSchools(Integer pageNum, Integer perPage) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));


        Page<School> page = schoolRepository.findAll(pageable);
        return new PageWrapper<>(page.getTotalElements(), schoolMapper.mapToList(page.getContent()));
    }

}
