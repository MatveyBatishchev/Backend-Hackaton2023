package ru.hackaton.backend.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hackaton.backend.models.domain.Achievement;
import ru.hackaton.backend.models.domain.AchievementType;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(value = """
            SELECT
                a.id, title, success_info, painting_name, painting_caption,
                painting_description, image, achievement_type,
                CASE WHEN u.user_id = :user_id THEN true ELSE false END AS received
            FROM main.achievement a
            LEFT JOIN main.user_achievement u ON a.id = u.achievement_id AND u.user_id = :user_id
            ORDER BY a.id""", nativeQuery = true)
    Page<Achievement> findAllWithUserReceived(@Param("user_id") Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO main.user_achievement(user_id, achievement_id) VALUES (:user_id, :achievement_id)",
            nativeQuery = true)
    void addAchievementToUser(@Param("user_id") long userId, @Param("achievement_id") long achievementId);

    List<Achievement> findAllByUserId(Long userId);

    Achievement findByAchievementType(AchievementType achievementType);

}