package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.ArticleDto;
import ru.hackaton.backend.services.ArticleService;
import ru.hackaton.backend.util.PageWrapper;

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
    public PageWrapper<ArticleDto> readAll(Integer pageNum, Integer perPage, String search, List<Long> articleTypeIds, List<Long> artIds) {
        return articleService.getAllArticles(pageNum, perPage, search, articleTypeIds, artIds);
    }

}