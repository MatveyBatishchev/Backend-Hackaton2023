package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Article;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(attributePaths = {"articleContent", "articleType"})
    @Override
    @NonNull
    Optional<Article> findById(@NonNull Long aLong);

    @EntityGraph(attributePaths = "articleType")
    @Override
    @NonNull
    Page<Article> findAll(@NonNull Pageable pageable);
}