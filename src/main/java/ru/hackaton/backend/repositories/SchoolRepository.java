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
import ru.hackaton.backend.models.domain.School;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    @EntityGraph(attributePaths = {"schoolContent", "arts", "studyPrograms", "district"})
    @Override
    @NonNull
    Optional<School> findById(@NonNull Long aLong);

    @EntityGraph(attributePaths = {"arts", "district"})
    @Override
    @NonNull
    Page<School> findAll(@NonNull Pageable pageable);

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

}