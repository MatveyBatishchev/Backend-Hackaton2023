package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.ArticleDto;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Articles")
@RequestMapping("/articles")
@CrossOrigin
public interface ArticleController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ArticleDto create(@RequestBody ArticleDto articleDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ArticleDto read(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody ArticleDto articleDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ArticleDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                             @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage);

}