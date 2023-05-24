package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.StudyProgramDto;
import ru.hackaton.backend.util.PageWrapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Study Program")
@RequestMapping("/study-programs")
public interface StudyProgramController {

    @Operation(description = "Id при создании игнорируется, его можно не передавать")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    StudyProgramDto create(@RequestBody StudyProgramDto studyProgramDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    StudyProgramDto read(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody StudyProgramDto studyProgramDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<StudyProgramDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                         @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage);

}