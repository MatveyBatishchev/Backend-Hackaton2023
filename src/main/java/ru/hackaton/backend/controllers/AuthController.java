package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hackaton.backend.models.auth.AuthRequest;
import ru.hackaton.backend.models.auth.AuthResponse;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Tag(name = "Auth")
@RequestMapping(value = "/auth", produces = APPLICATION_JSON_VALUE)
public interface AuthController {

    @PostMapping(path = "/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "Аутентификация в системе")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse authenticate(@Valid AuthRequest authRequest);

    @PostMapping("/refresh")
    @Operation(summary = "Возвращает обновлённый accessToken и прежний refreshToken")
    @ResponseStatus(HttpStatus.OK)
    void refresh(HttpServletRequest request, HttpServletResponse response);

}