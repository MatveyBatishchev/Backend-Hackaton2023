package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.mappers.ArticleMapper;
import ru.hackaton.backend.models.domain.Article;
import ru.hackaton.backend.repositories.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private Article findArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Статья с id " + id + " не была найдена!"));
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        Article newArticle = articleMapper.toArticle(articleDto);
        newArticle.setCreatedAt(LocalDateTime.now());
        return articleMapper.toDto(articleRepository.save(newArticle));
    }

    public ArticleDto getArticleById(long id) {
        return articleMapper.toDto(findArticleById(id));
    }

    public void updateArticle(long id, ArticleDto articleDto) {
        Article updatedArticle = articleMapper.toArticle(articleDto);
        updatedArticle.setId(id);
        articleRepository.save(updatedArticle);
    }

    public void deleteArticleById(long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDto> getAllArticles(Integer pageNum, Integer perPage) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));
        return articleMapper.mapToList(articleRepository.findAll(pageable).getContent());
    }

}