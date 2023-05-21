package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.models.domain.Test;
import ru.hackaton.backend.models.domain.Test_;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {


    @Override
    @NonNull
    Page<Test> findAll(Specification<Test> spec, @NonNull Pageable pageable);


    interface Specs {

        static Specification<Test> difficultyEquals(Difficulty difficulty) {
            return (root, query, builder) -> builder.equal(root.get(Test_.DIFFICULTY), difficulty);
        }

    }
}
