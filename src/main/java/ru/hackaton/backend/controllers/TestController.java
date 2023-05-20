package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.TestDto;
import ru.hackaton.backend.models.domain.Difficulty;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Tests")
@RequestMapping("/tests")
@CrossOrigin()
public interface TestController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TestDto create(@RequestBody TestDto testDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    TestDto read(@PathVariable("id") long id);


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody TestDto testDto);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TestDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                          @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage,
                          @Parameter(description = "Сложность теста (LITE, INTERMEDIATE, HARD)") @RequestParam(value = "difficulty", required = false) Difficulty difficulty);

}
