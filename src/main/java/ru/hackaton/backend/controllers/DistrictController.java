package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.DistrictDto;
import ru.hackaton.backend.util.PageWrapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "District")
@RequestMapping("/districts")
public interface DistrictController {

    @Operation(description = "Id при создании игнорируется, его можно не передавать")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DistrictDto create(@RequestBody DistrictDto districtDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    DistrictDto read(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody DistrictDto districtDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<DistrictDto> readAll();

}
