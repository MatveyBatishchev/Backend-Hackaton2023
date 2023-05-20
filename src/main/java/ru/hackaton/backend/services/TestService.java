package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.TestDto;
import ru.hackaton.backend.mappers.TestMapper;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.models.domain.Test;
import ru.hackaton.backend.repositories.TestRepository;

import static ru.hackaton.backend.repositories.TestRepository.Specs.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private final TestRepository testRepository;

    private final TestMapper testMapper;

    public TestDto getTestById(long id) {
        return testMapper.toDto(findTestById(id));
    }

    public Test findTestById(long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тест с id %d не был найден!".formatted(id)));

        return test;
    }

    public TestDto createTest(TestDto testDto) {
        Test testToSave = testMapper.toTest(testDto);
        Test savedTest = testRepository.save(testToSave);

        return testMapper.toDto(savedTest);
    }

    public void updateTest(long id, TestDto testDto) {
//        Test oldTest = findTestById(id);
        Test newTest = testMapper.toTest(testDto);

        testRepository.save(newTest);

    }

    public void deleteTest(long id) {
        testRepository.deleteById(id);
    }

    public List<TestDto> getAllTests(int pageNum, int perPage, Difficulty difficulty) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));

        Specification<Test> spec = Specification.where(null);
        if (difficulty != null) spec = spec.and(difficultyEquals(difficulty));

        Page<Test> tests = testRepository.findAll(spec, pageable);

        return testMapper.toDtoList(tests.getContent());

    }

}
