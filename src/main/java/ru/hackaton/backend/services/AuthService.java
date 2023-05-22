package ru.hackaton.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.hackaton.backend.config.security.jwt.JwtService;
import ru.hackaton.backend.errors.handler.ApiError;
import ru.hackaton.backend.models.auth.AuthRequest;
import ru.hackaton.backend.models.auth.AuthResponse;
import ru.hackaton.backend.models.auth.OAuthRequest;
import ru.hackaton.backend.models.auth.TokenType;
import ru.hackaton.backend.models.domain.MyUserDetails;
import ru.hackaton.backend.models.domain.User;
import ru.hackaton.backend.models.domain.UserRole;
import ru.hackaton.backend.repositories.UserRepository;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final ClientRegistrationRepository clientRegistrationRepository;

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Map<String, String> tokens = jwtService.generateTokens(userDetails);
        return new AuthResponse(tokens.get("access_token"), tokens.get("refresh_token"));
    }

    @SneakyThrows
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                final String refreshToken = authorizationHeader.substring("Bearer ".length());
                final String userEmail = jwtService.extractUsername(refreshToken);
                final String tokenType = jwtService.extractTokenType(refreshToken);

                if (tokenType == null || !tokenType.equals(TokenType.REFRESH_TOKEN.getName()))
                    throw new AccessDeniedException("Invalid token");

                if (userEmail != null) {
                    MyUserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                    if (jwtService.isTokenValid(refreshToken)) {
                        AuthResponse authResponse = new AuthResponse(jwtService.generateAccessToken(userDetails), refreshToken);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                    }
                }
            } catch (Exception exception) {
                ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, exception);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), apiError);
            }
        }
    }

    public AuthResponse authenticateOAuth(OAuthRequest oAuthRequest) {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(oAuthRequest.getOAuthProvider());
        if (clientRegistration == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid access_token or oAuth_provider");
        }

        User user = null;
        if (clientRegistration.getRegistrationId().equals("vk")) {
            String userEmail = oAuthRequest.getUserEmail();

            Optional<User> optional = userRepository.findByEmail(userEmail);
            if (optional.isEmpty()) {
                OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();
                OAuth2UserRequest userRequest = new OAuth2UserRequest(
                        clientRegistration,
                        new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, oAuthRequest.getAccessToken(), Instant.now(), Instant.MAX)
                );
                OAuth2User oAuth2User = userService.loadUser(userRequest);

                ArrayList<LinkedHashMap<String, Object>> responseArray = oAuth2User.getAttribute("response");
                LinkedHashMap<String, Object> userAttributes = responseArray.get(0);
                user = userRepository.save(
                        new User(userEmail,
                                userAttributes.get("first_name") + " " + userAttributes.get("last_name"),
                                userAttributes.get("photo_max").toString(),
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                List.of(UserRole.USER, UserRole.VK_USER)));
            } else {
                user = optional.get();
            }
        }
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid access_token or oAuth_provider");

        Map<String, String> tokens = jwtService.generateTokens(new MyUserDetails(user));
        return new AuthResponse(tokens.get("access_token"), tokens.get("refresh_token"));
    }

}