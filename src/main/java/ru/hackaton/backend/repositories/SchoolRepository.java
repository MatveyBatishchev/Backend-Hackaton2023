package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
