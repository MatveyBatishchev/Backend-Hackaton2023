package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.NewsDto;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "News")
@RequestMapping("/news")
public interface NewsController {

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void create(@RequestBody NewsDto newsDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    NewsDto read(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody NewsDto newsDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @GetMapping(value = "/list", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<NewsDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                          @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage,
                          @RequestParam(value = "categoryId", required = false) Integer categoryId,
                          @RequestParam(value = "include_categories", defaultValue = "false", required = false) Boolean includeCategories);

    @Operation(summary = "Привязывает к новости с указанным id соответствующие категории")
    @PostMapping("/{id}/categories")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void attachCategories(@PathVariable("id") long id, @RequestParam("category_ids") long[] categoryIds);

    @Operation(summary = "Отвязывает от новости с указанным id соответствующие категории")
    @DeleteMapping("/{id}/categories")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void detachCategories(@PathVariable("id") long id, @RequestParam("category_ids") long[] categoryIds);


}