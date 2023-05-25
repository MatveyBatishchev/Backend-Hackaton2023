package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.models.domain.UserTest;

public interface UserTestRepository extends ReadOnlyRepository<UserTest, Long> {


    @NonNull
    @Query(nativeQuery = true,
            value = """

                     SELECT t.id AS test_id,
                        t.title,
                        t.description,
                        t.image,
                        t.created_at,
                        t.updated_at,
                        t.score_per_question,
                        t.difficulty,
                        a.name AS art,
                        ut.user_id,
                        ut.score,
                        ut.passed_at
                       FROM main.test t
                         LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
                         JOIN main.art a ON t.art_id = a.id
                    """, countQuery = """

            SELECT count(t.id)
               FROM main.test t
                 LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
            """)
    Page<UserTest> findAll(@Param("user_id") long userId, @NonNull Pageable pageable);


    @NonNull
    @Query(nativeQuery = true,
            value = """

                     SELECT t.id AS test_id,
                        t.title,
                        t.description,
                        t.image,
                        t.created_at,
                        t.updated_at,
                        t.score_per_question,
                        t.difficulty,
                        a.name AS art,
                        ut.user_id,
                        ut.score,
                        ut.passed_at
                       FROM main.test t
                         LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
                         JOIN main.art a ON t.art_id = a.id
                     WHERE lower(a.name) = :art_name
                    """, countQuery = """

            SELECT count(t.id)
               FROM main.test t
                 LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
             WHERE lower(a.name) = :art_name
            """)
    Page<UserTest> findAllWhereArtNameEquals(@Param("user_id") long userId,
                                             @Param("art_name") String artName,
                                             @NonNull Pageable pageable);


//    interface Specs {
//
//        static Specification<UserTest> difficultyEquals(Difficulty difficulty) {
//            return (root, query, builder) -> builder.equal(root.get(UserTest_.difficulty), difficulty.toString());
//        }
//
//        static Specification<UserTest> artNameEquals(String artName) {
//            return (root, query, builder) -> builder.equal(root.get(UserTest_.art), artName);
//        }
//
//    }

}
