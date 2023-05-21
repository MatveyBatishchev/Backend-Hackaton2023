package ru.hackaton.backend.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.hackaton.backend.errors.handler.ApiError;
import ru.hackaton.backend.models.auth.TokenType;
import ru.hackaton.backend.models.domain.MyUserDetails;
import ru.hackaton.backend.models.domain.User;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                final String token = authorizationHeader.substring("Bearer ".length());
                final String tokenType = jwtService.extractTokenType(token);
                final String userEmail = jwtService.extractUsername(token);

                if (tokenType == null || !tokenType.equals(TokenType.ACCESS_TOKEN.getName()))
                    throw new AccessDeniedException("Invalid token");

                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (jwtService.isTokenValid(token)) {
                        List<String> roles = jwtService.extractRoles(token);
                        if (roles == null) throw new AccessDeniedException("Invalid token");

                        MyUserDetails userDetails = new MyUserDetails(new User(jwtService.extractId(token), userEmail));
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                roles.stream().map(SimpleGrantedAuthority::new).toList()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        filterChain.doFilter(request, response);
                    }
                }
            } catch (Exception exception) {
                ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exception);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), apiError);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
