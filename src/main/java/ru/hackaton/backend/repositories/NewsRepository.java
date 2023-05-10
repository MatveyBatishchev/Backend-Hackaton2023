package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.models.domain.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query(value = """
            SELECT
            new ru.hackaton.backend.dtos.NewsDto(
                n.id,
                n.name,
                n.description,
                n.image,
                n.published,
                n.createdAt,
                n.updatedAt
            )
            FROM
                News n
            ORDER BY n.updatedAt DESC""")
    Page<NewsDto> findAllButContent(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO main.news_category(news_id, category_id) VALUES (:newsId, unnest(:categoryIds))",
            nativeQuery = true)
    void saveNewsCategories(@Param(value = "newsId") long newsId, @Param(value = "categoryIds") long[] categoryIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM main.news_category WHERE news_id=:newsId AND category_id IN :categoryIds",
            nativeQuery = true)
    void deleteNewsCategories(@Param(value = "newsId") long newsId, @Param(value = "categoryIds") long[] categoryIds);

}
