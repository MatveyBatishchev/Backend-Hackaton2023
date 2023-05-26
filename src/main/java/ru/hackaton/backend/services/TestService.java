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

import java.util.List;

import static ru.hackaton.backend.repositories.TestRepository.Specs.artIdEquals;
import static ru.hackaton.backend.repositories.TestRepository.Specs.difficultyEquals;

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
        Test updatedTest = testMapper.toTest(testDto);
        updatedTest.setId(id);

        testRepository.save(updatedTest);

    }

    public void deleteTest(long id) {
        testRepository.deleteById(id);
    }

    public List<TestDto> getAllTests(int pageNum, int perPage, Difficulty difficulty, Long artId) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));

        Specification<Test> spec = Specification.where(null);
        if (difficulty != null) spec = spec.and(difficultyEquals(difficulty));
        if (artId != null) spec = spec.and(artIdEquals(artId));

        Page<Test> tests = testRepository.findAll(spec, pageable);

        return testMapper.toDtoList(tests.getContent());
    }

    public long getCount(Long artId) {
        Specification<Test> spec = null;

        if (artId != null) spec = Specification.where(artIdEquals(artId));

        return testRepository.count(spec);
    }

    public long getScoreSum(Long artId) {

        if (artId != null)
            return testRepository.findScoreSum(artId);

        return testRepository.findScoreSum();

    }
}
