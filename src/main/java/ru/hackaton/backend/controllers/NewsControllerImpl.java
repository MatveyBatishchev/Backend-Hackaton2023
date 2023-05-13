package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.services.NewsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {

    private final NewsService newsService;

    @Override
    public void create(NewsDto newsDto) {
        newsService.createNews(newsDto);
    }

    @Override
    public NewsDto read(long id) {
        return newsService.findNewsById(id);
    }

    @Override
    public void update(long id, NewsDto newsDto) {
        newsService.updateNews(id, newsDto);
    }

    @Override
    public void delete(long id) {
        newsService.deleteNewsById(id);
    }

    @Override
    public List<NewsDto> readAll(Integer pageNum, Integer perPage, Boolean includeCategories) {
        return newsService.findAllNews(pageNum, perPage, includeCategories);
    }

    @Override
    public void attachCategories(long id, long[] categoryIds) {
        newsService.attachCategoriesToNews(id, categoryIds);
    }

    @Override
    public void detachCategories(long id, long[] categoryIds) {
        newsService.detachCategoriesToNews(id, categoryIds);
    }

}