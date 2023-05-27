package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Art_;
import ru.hackaton.backend.models.domain.Course;
import ru.hackaton.backend.models.domain.Course_;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {


    @EntityGraph(attributePaths = {"content", "lessons"})
    @Override
    @NonNull
    Optional<Course> findById(@NonNull Long id);


    @Override
    @NonNull
    Page<Course> findAll(Specification<Course> spec, @NonNull Pageable pageable);


    interface Specs {

        static Specification<Course> artIdEquals(Long artId) {
            return (root, query, builder) -> builder.equal(root.get(Course_.ART).get(Art_.ID), artId);
        }


    }
}