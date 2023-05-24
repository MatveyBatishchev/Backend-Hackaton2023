package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnswerDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String text;

    private String video;

    private String audio;

    private String image;

    private Boolean isCorrect;


}