package ru.hackaton.backend.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OAuthRequest {

    @JsonProperty("access_token")
    @Schema(description = "Access token полученный черз Authorization Code Flow от сервера авторизации", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String accessToken;

    @JsonProperty("oAuth_provider")
    @Schema(description = "Провайдер сервера авторизации", defaultValue = "vk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String oAuthProvider;

    @JsonProperty("user_email")
    @Schema(description = "Опциональное поле email, при авторизации через VK")
    private String userEmail;

}
