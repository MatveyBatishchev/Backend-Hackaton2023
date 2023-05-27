package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hackaton.backend.models.domain.Achievement;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(value = """
            SELECT
                a.id, title, success_info, painting_name, painting_caption,
                painting_description, image, achievement_type,
            CASE WHEN u.user_id = :userId THEN true ELSE false END AS received
            FROM main.achievement a
            LEFT JOIN main.user_achievement u ON a.id = u.achievement_id
            ORDER BY a.id""", nativeQuery = true)
    Page<Achievement> findAllWithUserReceived(Long userId, Pageable pageable);

    List<Achievement> findAllByUserId(Long userId);

}