package ru.hackaton.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SchoolDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(example = "{\"json\": true}")
    private String content;

    private DistrictDto district;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<ArtDto> arts;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<StudyProgramDto> studyPrograms;

}
