package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTestDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long testId;

    private Integer score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime passedAt;

}
