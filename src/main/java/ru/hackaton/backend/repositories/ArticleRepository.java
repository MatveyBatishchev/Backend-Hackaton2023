package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Article;
import ru.hackaton.backend.models.domain.ArticleType_;
import ru.hackaton.backend.models.domain.Article_;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @EntityGraph(attributePaths = {"articleContent", "articleType", "arts"})
    @Override
    @NonNull
    Optional<Article> findById(@NonNull Long aLong);

    @EntityGraph(attributePaths = {"articleType", "arts"})
    @Override
    @NonNull
    Page<Article> findAll(Specification<Article> spec, @NonNull Pageable pageable);

    interface Specs {
        static Specification<Article> nameLike(String name) {
            return (root, query, builder) -> builder.like(builder.lower(root.get(Article_.NAME)), "%" + name.toLowerCase() + "%");
        }

        static Specification<Article> articleTypeEquals(Long articleTypeId) {
            return (root, query, builder) -> builder.equal(root.get(Article_.ARTICLE_TYPE).get(ArticleType_.ID), articleTypeId);
        }
    }

}