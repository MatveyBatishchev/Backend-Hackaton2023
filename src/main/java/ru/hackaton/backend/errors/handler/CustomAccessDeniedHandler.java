package ru.hackaton.backend.errors.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        buildResponse(accessDeniedException, request.getContextPath(), response);
    }

    private void buildResponse(Exception exception, String contextPath, HttpServletResponse response) throws IOException {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("context-path", contextPath);
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("error", exception.getClass().getSimpleName());
        responseBody.put("reason", exception.getMessage());

        response.setStatus(401);
        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

}

