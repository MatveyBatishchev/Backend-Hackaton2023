package ru.hackaton.backend.dtos;

import lombok.Data;

@Data
public class AchievementDto {

    private long id;

    private String title;

    private String image;

    private boolean received;

    private String successInfo;

    private String paintingName;

    private String paintingCaption;

    private String paintingDescription;

}
