package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String text;

    private String video;

    private String audio;

    private int score;

    private Set<AnswerDto> answers;

}
