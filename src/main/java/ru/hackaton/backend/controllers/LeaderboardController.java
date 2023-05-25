package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hackaton.backend.dtos.UserDto;
import ru.hackaton.backend.util.PageWrapper;

@Tag(name = "Leaderboard")
@RequestMapping("/leaderboard")
public interface LeaderboardController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Возвращает топ пользователей в рейтинге по очкам")
    PageWrapper<UserDto> read(@Parameter(description = "Сколько пользователей нужно получить") @RequestParam(value = "limit", defaultValue = "10") int limit);

}
