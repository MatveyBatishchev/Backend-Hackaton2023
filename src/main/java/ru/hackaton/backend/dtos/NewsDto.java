package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
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

    private boolean published;

    @Transient
    @Schema(example = "{\"json\": true}")
    private String content;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Transient
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<CategoryDto> categoryDtos;

}
