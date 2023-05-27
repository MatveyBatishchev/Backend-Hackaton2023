package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Art_;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.models.domain.Test;
import ru.hackaton.backend.models.domain.Test_;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {


    @EntityGraph(attributePaths = {"art"})
    @Override
    @NonNull
    Optional<Test> findById(@NonNull Long id);

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"art"})
    Page<Test> findAll(Specification<Test> spec, @NonNull Pageable pageable);

    @Query(value = "SELECT SUM(t.scorePerQuestion * size(t.questions)) FROM Test t WHERE t.art.id = :art_id")
    Long findScoreSum(@Param("art_id") Long artId);

    @Query(value = "SELECT SUM(t.scorePerQuestion * size(t.questions)) FROM Test t")
    Long findScoreSum();

    @Query(value = "SELECT SUM(t.scorePerQuestion * size(t.questions)) FROM Test t WHERE t.id = :test_id")
    Long findScoreSumByTestId(@Param("test_id") long testId);

    interface Specs {

        static Specification<Test> difficultyEquals(Difficulty difficulty) {
            return (root, query, builder) -> builder.equal(root.get(Test_.DIFFICULTY), difficulty);
        }

        static Specification<Test> artIdEquals(Long artId) {
            return (root, query, builder) -> builder.equal(root.get(Test_.ART).get(Art_.ID), artId);
        }


    }
}
