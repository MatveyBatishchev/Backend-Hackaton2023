package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.ArticleTypeDto;
import ru.hackaton.backend.util.PageWrapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Article Types")
@RequestMapping("/article_types")
public interface ArticleTypeController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Id при создании игнорируется, его можно не передавать")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ArticleTypeDto create(@RequestBody ArticleTypeDto articleTypeDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ArticleTypeDto read(@PathVariable("id") long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody ArticleTypeDto articleTypeDto);

    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<ArticleTypeDto> readAll();

}