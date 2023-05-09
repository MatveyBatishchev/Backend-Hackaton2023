package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // FIXME: doesnt work as hibernate still wants 'content' field while mapping ResultSet to News.class
    // Probably projection can be a solution
    @Query(value = """
            SELECT
                n.id,
                n.created_at,
                n.description,
                n.image,
                n.name,
                n.published,
                n.updated_at
            FROM
                main.news n
            ORDER BY updated_at DESC
            LIMIT :perPage OFFSET :perPage * :pageNum""", nativeQuery = true)
    List<News> findAllCompressed(@Param(value = "pageNum") int pageNum, @Param(value = "perPage") int perPage);

}
