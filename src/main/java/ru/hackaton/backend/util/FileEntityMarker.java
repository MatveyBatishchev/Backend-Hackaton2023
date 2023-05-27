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

    SCHOOL("schools"),

    TEST("tests");

    private final String name;

}
