package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hackaton.backend.models.auth.AuthRequest;
import ru.hackaton.backend.models.auth.AuthResponse;
import ru.hackaton.backend.models.auth.OAuthRequest;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Tag(name = "Auth")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public interface AuthController {

    @PostMapping(path = "/auth/login", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "Аутентификация в системе")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse authenticate(@Valid AuthRequest authRequest);

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/auth/refresh")
    @Operation(summary = "Возвращает обновлённый accessToken и прежний refreshToken",
            description = "Bearer refresh токен должен быть прикреплён в заголовке Authorization")
    @ResponseStatus(HttpStatus.OK)
    void refresh(HttpServletRequest request, HttpServletResponse response);

    @PostMapping(path = "/oAuth/login")
    @Operation(summary = "Аутентификация в системе при помощи VK OAuth 2.0")
    @ResponseStatus(HttpStatus.OK)
    AuthResponse authenticateOAuth(@Valid @RequestBody OAuthRequest oAuthRequest);

}