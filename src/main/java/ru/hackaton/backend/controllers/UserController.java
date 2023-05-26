package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.dtos.UserTestDto;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.models.domain.UserTest;
import ru.hackaton.backend.util.PageWrapper;
import ru.hackaton.backend.util.UploadFileResponse;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@Tag(name = "Users")
@RequestMapping("/users")
public interface UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto create(@RequestBody @Valid UserDto userDto);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UserDto read(@PathVariable("id") long id);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @Operation(description = "Роли пользователя при вызове данного endpoint-а не изменяются")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @RequestBody @Valid UserDto userDto);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Устанавливает роли пользователя (перезаписывая старые)")
    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateUserRoles(@PathVariable("id") long id,
                         @RequestParam List<UserRole> roles);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #id == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<UserDto> readAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                 @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage);

    @Operation(summary = "Загрузка аватара пользователя")
    @PutMapping(value = "/{id}/avatar", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UploadFileResponse uploadAvatar(@PathVariable("id") long id,
                                    @RequestParam("file") MultipartFile file);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #userId == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @Operation(summary = "Устанавливает, сколько очков пользователь получил за прохождение теста")
    @PutMapping("/{userId}/tests/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateUserTest(@PathVariable("userId") long userId,
                        @PathVariable("testId") long testId,
                        @RequestBody UserTestDto userTestDto);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #userId == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @Operation(summary = "Удаляет данные пользователя о прохождении данного теста")
    @DeleteMapping("/{userId}/tests/{testId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserTest(@PathVariable("userId") long userId,
                        @PathVariable("testId") long testId);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('USER') and #userId == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @Operation(summary = """
            Возвращает все тесты вообще, а также результаты пользователя за эти тесты. 
            Если пользователь ещё не проходил какой-то тест, то поля результатов будут null
            """)
    @GetMapping(value = "/{userId}/tests", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    PageWrapper<UserTest> readAllUserTests(@PathVariable("userId") long userId,
                                           @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNum,
                                           @RequestParam(value = "per_page", defaultValue = "25", required = false) Integer perPage,
                                           @RequestParam(value = "art_id", required = false) Long artId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Возвращает текущую позицию пользователя в рейтинге")
    @GetMapping("/{userId}/position")
    @ResponseStatus(HttpStatus.OK)
    int getUserPosition(@PathVariable("userId") long userId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Возвращает количество тестов, пройденных пользователем")
    @PreAuthorize("hasAuthority('USER') and #userId == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @GetMapping("/{userId}/tests/count")
    @ResponseStatus(HttpStatus.OK)
    long getUserTestsCount(@PathVariable("userId") long userId,
                          @RequestParam(value = "art_id", required = false) Long artId);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Возвращает сумму очков за тесты, пройденные пользователем")
    @PreAuthorize("hasAuthority('USER') and #userId == (authentication.getPrincipal()).getId() or hasAuthority('ADMIN')")
    @GetMapping("/{userId}/tests/score-sum")
    @ResponseStatus(HttpStatus.OK)
    long getUserTestsScoreSum(@PathVariable("userId") long userId,
                             @RequestParam(value = "art_id", required = false) Long artId);

}