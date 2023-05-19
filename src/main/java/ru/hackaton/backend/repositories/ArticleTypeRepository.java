package ru.hackaton.backend.repositories;

import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.ArticleType;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Long> {

    @Named("getArticleTypeReferenceById")
    @Override
    @NonNull
    ArticleType getReferenceById(@NonNull Long aLong);
}