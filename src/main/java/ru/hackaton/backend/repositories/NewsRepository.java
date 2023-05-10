package ru.hackaton.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

}
