package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hackaton.backend.models.domain.StudyProgram;


public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
}