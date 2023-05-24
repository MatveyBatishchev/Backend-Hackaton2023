package ru.hackaton.backend.repositories;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {

    @EntityGraph(attributePaths = {"schoolContent", "arts", "studyPrograms", "district"})
    @Override
    @NonNull
    Optional<School> findById(@NonNull Long aLong);

    @EntityGraph(attributePaths = {"arts", "district"})
    @Override
    @NonNull
    Page<School> findAll(@NonNull Specification<School> spec, @NonNull Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM main.school_art WHERE school_id=:school_id", nativeQuery = true)
    void deleteArtsFromSchool(@Param("school_id") long schoolId);

    @Modifying
    @Query(value = "INSERT INTO main.school_art(school_id, art_id) VALUES (:school_id, unnest(:art_ids))", nativeQuery = true)
    void addArtsToSchool(@Param("school_id") long schoolId, @Param("art_ids") Long[] artIds);

    @Modifying
    @Query(value = "DELETE FROM main.school_study_program WHERE school_id=:school_id", nativeQuery = true)
    void deleteStudyProgramsFromSchool(@Param("school_id") long schoolId);

    @Modifying
    @Query(value = "INSERT INTO main.school_study_program(school_id, study_program_id) VALUES (:school_id, unnest(:study_program_ids))", nativeQuery = true)
    void addStudyProgramsToSchool(@Param("school_id") long schoolId, @Param("study_program_ids") Long[] studyProgramIds);

    interface Specs {
        static Specification<School> nameLike(String name) {
            return (root, query, builder) -> builder.like(builder.lower(root.get(School_.NAME)), "%" + name.toLowerCase() + "%");
        }

        static Specification<School> districtIdIn(List<Long> districtIds) {
            return (root, query, builder) -> {
                Path<Long> districtIdPath = root.get(School_.DISTRICT).get(District_.ID);
                return districtIdPath.in(districtIds);
            };
        }

        static Specification<School> artsContainsAll(List<Long> artIds) {
            return (root, query, builder) -> {
                Predicate[] predicates = new Predicate[artIds.size()];
                Join<School, Art> artsJoin = root.join(School_.ARTS);
                Expression<List<Long>> artIdsExpression = artsJoin.get(Art_.ID);

                for (int i = 0; i < artIds.size(); i++) {
                    predicates[i] = builder.literal(artIds.get(i)).in(artIdsExpression);
                }

                return builder.or(predicates);
            };
        }

    }

}