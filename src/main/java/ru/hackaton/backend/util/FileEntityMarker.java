package ru.hackaton.backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileEntityMarker {

    ARTICLE("articles"),

    ANSWER("answers"),

    QUESTION("questions"),

    USER("users"),

    TEST("tests");

    private final String name;

}
