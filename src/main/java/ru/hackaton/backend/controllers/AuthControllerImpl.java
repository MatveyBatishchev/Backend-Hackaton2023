package ru.hackaton.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.models.auth.AuthRequest;
import ru.hackaton.backend.models.auth.AuthResponse;
import ru.hackaton.backend.services.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public AuthResponse authenticateOAuth(AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
    }

    @Override
    public AuthResponse authenticateOAuth(String accessToken, String userEmail) {
        return authService.authenticateOAuth(accessToken, userEmail);
    }

}
