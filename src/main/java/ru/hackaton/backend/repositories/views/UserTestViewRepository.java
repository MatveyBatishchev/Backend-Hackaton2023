package ru.hackaton.backend.repositories.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import ru.hackaton.backend.models.domain.Difficulty;
import ru.hackaton.backend.models.domain.Test;
import ru.hackaton.backend.models.domain.Test_;
import ru.hackaton.backend.models.domain.views.UserTestView;
import ru.hackaton.backend.repositories.ReadOnlyRepository;

public interface UserTestViewRepository extends ReadOnlyRepository<UserTestView, Long> {


    @Override
    @NonNull
    Page<UserTestView> findAll(Specification<UserTestView> spec, @NonNull Pageable pageable);


    interface Specs {

        static Specification<Test> difficultyEquals(Difficulty difficulty) {
            return (root, query, builder) -> builder.equal(root.get(Test_.DIFFICULTY), difficulty);
        }

    }

}
