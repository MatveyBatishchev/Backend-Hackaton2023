package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.TestDto;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.services.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestControllerImpl implements TestController {

    private final TestService testService;


    @Override
    public TestDto create(TestDto testDto) {
        return testService.createTest(testDto);
    }

    @Override
    public TestDto read(long id) {
        return testService.getTestById(id);
    }

    @Override
    public void update(long id, TestDto testDto) {
        testService.updateTest(id, testDto);
    }

    @Override
    public void delete(long id) {
        testService.deleteTest(id);
    }

    @Override
    public List<TestDto> readAll(Integer pageNum, Integer perPage, Difficulty difficulty, Long artId) {
        return testService.getAllTests(pageNum, perPage, difficulty, artId);
    }

    @Override
    public Long getCount(Long artName) {
        return testService.getCount(artName);
    }

    @Override
    public Long getScoreSum(Long artName) {
        return testService.getScoreSum(artName);
    }
}
