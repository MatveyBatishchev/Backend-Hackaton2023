package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

}
