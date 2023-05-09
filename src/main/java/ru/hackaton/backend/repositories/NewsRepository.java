package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {}
