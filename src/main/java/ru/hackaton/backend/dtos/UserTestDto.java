package ru.hackaton.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserTestDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long testId;

    private Integer score;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime passedAt;

    // For spring projections as it doesn't resolve lombok constructor
    public UserTestDto(Long userId, Long testId, Integer score, LocalDateTime passedAt) {
        this.userId = userId;
        this.testId = testId;
        this.score = score;
        this.passedAt = passedAt;
    }
}
