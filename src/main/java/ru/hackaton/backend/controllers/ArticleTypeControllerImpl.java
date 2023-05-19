package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.ArticleTypeDto;
import ru.hackaton.backend.services.ArticleTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleTypeControllerImpl implements ArticleTypeController {

    private final ArticleTypeService articleTypeService;

    @Override
    public ArticleTypeDto create(ArticleTypeDto articleTypeDto) {
        return articleTypeService.createArticleType(articleTypeDto);
    }

    @Override
    public ArticleTypeDto read(long id) {
        return articleTypeService.getArticleTypeById(id);
    }

    @Override
    public void update(long id, ArticleTypeDto articleTypeDto) {
        articleTypeService.updateArticleType(id, articleTypeDto);
    }

    @Override
    public void delete(long id) {
        articleTypeService.deleteArticleTypeById(id);
    }

    @Override
    public List<ArticleTypeDto> readAll() {
        return articleTypeService.getAllArticleTypes();
    }

}