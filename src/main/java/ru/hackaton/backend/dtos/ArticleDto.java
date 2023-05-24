package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ArticleDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    private String description;

    private String image;

    private boolean published;

    @Schema(example = "{\"json\": true}")
    private String content;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    private ArticleTypeDto articleType;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<ArtDto> arts;

}