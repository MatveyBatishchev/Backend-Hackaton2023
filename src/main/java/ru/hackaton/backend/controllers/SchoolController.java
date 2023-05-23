package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.SchoolDto;
import ru.hackaton.backend.util.PageWrapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Schools")
@RequestMapping("/schools")
public interface SchoolController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SchoolDto create(@RequestBody SchoolDto schoolDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SchoolDto read(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody SchoolDto schoolDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<SchoolDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                   @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage);


    @Operation(summary = "Устанавливает направления для школы (перезаписывая старые)")
    @PutMapping("/{id}/arts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateSchoolArts(@PathVariable("id") long id,
                          @RequestParam("art_ids") Long[] artIds);

    // Can be easily divided into 2 endpoints
    // Or endpoint accepting {add:[], remove[]}
    @Operation(summary = "Устанавливает учебные программы для школы (перезаписывая старые)")
    @PutMapping("/{id}/study-programs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateSchoolStudyPrograms(@PathVariable("id") long id,
                                   @RequestParam("study_program_ids") Long[] studyProgramIds);


}