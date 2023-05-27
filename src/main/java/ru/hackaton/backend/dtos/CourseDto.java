package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.hackaton.backend.models.domain.Art;
import ru.hackaton.backend.models.domain.StudyProgram;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CourseDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    private String name;

    private String image;

    @Schema(example = "{\"json\": true}")
    private String content;

    private String description;

    private Long price;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer duration;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;

    private ArtDto art;

    private Set<LessonDto> lessons;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StudyProgramDto studyProgram;

}
