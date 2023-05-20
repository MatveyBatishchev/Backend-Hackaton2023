package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.util.PageWrapper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Articles")
@RequestMapping("/articles")
public interface ArticleController {

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ArticleDto create(@RequestBody ArticleDto articleDto);

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ArticleDto read(@PathVariable("id") long id);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Во вложенном объекте articleType воспринимется только id, поле name игнорируется (можно не указывать)")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody ArticleDto articleDto);

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @Operation(description = "Во вложенном объекте articleType воспринимется только id, поле name игнорируется (можно не указывать)")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<ArticleDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                    @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage,
                                    @Parameter(description = "Строковое значение для поиска по названиям статей") @RequestParam(value = "search", required = false) String search,
                                    @Parameter(description = "ID - значение для поиска по типам статей") @RequestParam(value = "article_type_id", required = false) Long articleTypeId);


}