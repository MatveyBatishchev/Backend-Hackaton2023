package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @NonNull
    @Override
    @EntityGraph(attributePaths = "roles")
    Page<User> findAll(@NonNull Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM main.user_role WHERE user_id=:user_id", nativeQuery = true)
    void deleteRolesFromUser(@Param("user_id") long userId);

    @Modifying
    @Query(value = "INSERT INTO main.user_role(user_id, role) VALUES (:user_id, unnest(:roles))", nativeQuery = true)
    void addRolesToUser(@Param("user_id") long userId, @Param("roles") String[] roles);

    @Modifying
    @Query(value = "DELETE FROM main.user_test WHERE user_id = :user_id AND test_id = :test_id", nativeQuery = true)
    void deleteUserTest(@Param("user_id") long userId, @Param("test_id") long testId);

    @Modifying
    @Query(value = """
                INSERT INTO main.user_test (user_id, test_id, score, passed_at)
                VALUES (:user_id, :test_id, :score, :passed_at)
            """, nativeQuery = true)
    void addUserTest(@Param("user_id") long userId,
                     @Param("test_id") long testId,
                     @Param("score") int score,
                     @Param("passed_at") LocalDateTime passedAt);

    @Query(value = """
            SELECT position
            FROM (
               SELECT id,
                    ROW_NUMBER() OVER(
                       ORDER BY score DESC
                    ) AS position
               FROM main.user
            ) result
            WHERE id = :user_id
            """, nativeQuery = true)
    int getUserPosition(@Param("user_id") long userId);

    @Modifying
    @Query(value = """
            UPDATE main.user
            SET avatar = :avatar
            WHERE id = :user_id
            """, nativeQuery = true)
    void updateUserAvatar(@Param("user_id") long id, @Param("avatar") String avatar);


}