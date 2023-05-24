package ru.hackaton.backend.repositories;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

                for (int i = 0; i < artIds.size(); i++) {
                    predicates[i] = builder.literal(artIds.get(i)).in(root.join(Article_.ARTS).get(Art_.ID));
                }

                return builder.and(predicates);
            };
        }


    }

}