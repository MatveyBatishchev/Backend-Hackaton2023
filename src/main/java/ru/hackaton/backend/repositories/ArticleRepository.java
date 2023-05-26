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
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @EntityGraph(attributePaths = {"articleContent", "articleType", "arts"})
    @Override
    @NonNull
    Optional<Article> findById(@NonNull Long aLong);

    @EntityGraph(attributePaths = {"articleType", "arts"})
    @Override
    @NonNull
    Page<Article> findAll(@NonNull Specification<Article> spec, @NonNull Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM main.article_art WHERE article_id=:article_id", nativeQuery = true)
    void deleteArtsFromArticle(@Param("article_id") long articleId);

    @Modifying
    @Query(value = "INSERT INTO main.article_art(article_id, art_id) VALUES (:article_id, unnest(:art_ids))", nativeQuery = true)
    void addArtsToArticle(@Param("article_id") long articleId, @Param("art_ids") Long[] artIds);

    interface Specs {
        static Specification<Article> nameLike(String name) {
            return (root, query, builder) -> builder.like(builder.lower(root.get(Article_.NAME)), "%" + name.toLowerCase() + "%");
        }

        static Specification<Article> articleTypeIdIn(List<Long> articleTypeIds) {
            return (root, query, builder) -> {
                Path<Long> articleTypeIdPath = root.get(Article_.ARTICLE_TYPE).get(ArticleType_.ID);
                return articleTypeIdPath.in(articleTypeIds);
            };
        }

        static Specification<Article> artsContainsAll(List<Long> artIds) {
            return (root, query, builder) -> {
                Predicate[] predicates = new Predicate[artIds.size()];
                Join<Article, Art> artsJoin = root.join(Article_.ARTS);
                Expression<List<Long>> artIdsExpression = artsJoin.get(Art_.ID);

                for (int i = 0; i < artIds.size(); i++) {
                    predicates[i] = builder.literal(artIds.get(i)).in(artIdsExpression);
                }

                return builder.or(predicates);
            };
        }

    }

}