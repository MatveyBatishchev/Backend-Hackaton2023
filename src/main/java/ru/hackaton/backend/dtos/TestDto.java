package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TestDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String title;

    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    private Set<QuestionDto> questions;

}
