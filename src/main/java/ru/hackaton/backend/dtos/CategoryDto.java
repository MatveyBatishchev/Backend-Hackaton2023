package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoryDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

}