package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.util.PageWrapper;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users")
@RequestMapping("/users")
public interface UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto create(@RequestBody UserDto userDto);

    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UserDto read(@PathVariable("id") long id);

    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @Operation(description = "Роли пользователя при вызове данного endpoint-а не изменяются")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody UserDto userDto);

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Изменение ролей пользователя")
    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody List<UserRole> roles);

    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<UserDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                 @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage);

}