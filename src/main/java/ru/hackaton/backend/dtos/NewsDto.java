package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class NewsDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    private String description;

    private String image;

    @Schema(example = "false")
    private boolean published;

    @Schema(example = "{\"json\": true}")
    private String content;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<CategoryDto> categories;

    // Compressed constructor - will be used automatically for spring projection in derived queries
    public NewsDto(long id, String name, String description, String image, boolean published, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.published = published;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
