package ru.hackaton.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AchievementResponse {

    private long userId;

    private long achievementId;

    private String achievementImage;

    private String achievementTitle;

    private String achievementMessage;

}
