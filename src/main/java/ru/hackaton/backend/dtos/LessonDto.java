package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private Integer lessonOrder;

    private String name;

    private String image;

    @Schema(example = "{\"json\": true}")
    private String content;

    private String description;

    private Integer duration;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    private Boolean intro;

    //Прошёл ли пользователь данный урок
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)

    private Boolean completed = false;
}
