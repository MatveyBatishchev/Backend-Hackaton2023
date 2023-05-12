package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}