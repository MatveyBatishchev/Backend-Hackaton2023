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
                    						a.id AS art_id,
                                            a.name AS art,
                    						COUNT(q.id) AS questions_size,
                                            ut.user_id,
                                            ut.score,
                    						ut.score / t.score_per_question as answers_size,
                                            ut.passed_at
                                           FROM main.test t
                                             LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
                    						 JOIN main.question q ON q.test_id = t.id
                                             JOIN main.art a ON t.art_id = a.id
                    						 LEFT JOIN main.user_test ON t.id = ut.test_id
                    GROUP BY t.id, a.name, a.id, ut.user_id, ut.score, ut.passed_at
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
                                            a.id AS art_id,
                                            a.name AS art,
                                            COUNT(q.id) AS questions_size,
                                            ut.user_id,
                                            ut.score,
                                            ut.score / t.score_per_question as answers_size,
                                            ut.passed_at
                                           FROM main.test t
                                             LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
                                             JOIN main.question q ON q.test_id = t.id
                                             JOIN main.art a ON t.art_id = a.id
                                             LEFT JOIN main.user_test ON t.id = ut.test_id
                                         WHERE a.id = :art_id
                    GROUP BY t.id, a.name, a.id, ut.user_id, ut.score, ut.passed_at
                                        """, countQuery = """

            SELECT count(t.id)
               FROM main.test t
                 LEFT JOIN main.user_test ut ON t.id = ut.test_id AND ut.user_id = :user_id
             WHERE a.id = :art_id
            """)
    Page<UserTest> findAllWhereArtIdEquals(@Param("user_id") long userId,
                                             @Param("art_id") Long artId,
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
