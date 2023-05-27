package ru.hackaton.backend.util;

import ru.hackaton.backend.models.domain.AchievementType;

import java.util.HashSet;
import java.util.Set;

import static ru.hackaton.backend.models.domain.AchievementType.*;


public enum AchievementCategory {
    TEST,
    COURSE;

    public final static Set<AchievementType> testTypes = Set.of(
            TEST_MORE_1, TEST_MORE_10, TEST_MORE_20,
            TEST_MAX_SCORE, TEST_3_IN_ROW_MAX_SCORE);

    public final static Set<AchievementType> profileTypes = Set.of(PROFILE_MORE_100_POINTS);

    public static Set<AchievementType> getTestTypes() {
        return new HashSet<>(testTypes);
    }

    public static Set<AchievementType> getProfileTypes() {
        return new HashSet<>(profileTypes);
    }

}
