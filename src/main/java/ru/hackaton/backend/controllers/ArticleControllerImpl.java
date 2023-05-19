package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.services.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleControllerImpl implements ArticleController {

    private final ArticleService articleService;

    @Override
    public ArticleDto create(ArticleDto articleDto) {
        return articleService.createArticle(articleDto);
    }

    @Override
    public ArticleDto read(long id) {
        return articleService.getArticleById(id);
    }

    @Override
    public void update(long id, ArticleDto articleDto) {
        articleService.updateArticle(id, articleDto);
    }

    @Override
    public void delete(long id) {
        articleService.deleteArticleById(id);
    }

    @Override
    public List<ArticleDto> readAll(Integer pageNum, Integer perPage) {
        return articleService.getAllArticles(pageNum, perPage);
    }

}