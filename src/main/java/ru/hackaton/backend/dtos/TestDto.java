package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String image;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    private int maxScore;

    @Schema(example = "LITE")
    private String difficulty;

    private ArtDto art;
    private Set<QuestionDto> questions;

}
