package ru.hackaton.backend.services;

import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.mappers.NewsMapper;
import ru.hackaton.backend.models.domain.News;
import ru.hackaton.backend.repositories.NewsRepository;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private static final Map<String, EntityGraph> FETCH_OPTIONS = Map.ofEntries(
            entry("all", DynamicEntityGraph.fetching(List.of("categories", "newsContent"))),
            entry("categories", DynamicEntityGraph.fetching(List.of("categories"))),
            entry("content", DynamicEntityGraph.fetching(List.of("newsContent")))
    );

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private News getNewsById(long id, EntityGraph entityGraph) {
        return newsRepository.findById(id, entityGraph).orElseThrow(() ->
                new EntityNotFoundException("Новость с id " + id + " не была найдена!"));
    }

    public void createNews(NewsDto newsDto) {
        newsRepository.save(newsMapper.toNews(newsDto));
    }

    public NewsDto findNewsById(long id) {
        return newsMapper.toDto(getNewsById(id, FETCH_OPTIONS.get("all")));
    }

    public void updateNews(long id, NewsDto newsDto) {
        News dbNews = getNewsById(id, FETCH_OPTIONS.get("content"));
        newsDto.setId(id);
        newsMapper.updateNewsFromDto(newsDto, dbNews);

        // update content if it was sent
        if (newsDto.getContent() != null) dbNews.getNewsContent().setContent(newsDto.getContent());
        newsRepository.save(dbNews);
    }

    public void deleteNewsById(long id) {
        newsRepository.deleteById(id);
    }

    public List<NewsDto> findAllNews(Integer pageNum, Integer perPage, Integer categoryId, Boolean includeCategories) {
        perPage = Math.min(perPage, 100);
        Pageable pageable = PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION));

        Page<News> news;
        if (categoryId != null) {
            news = includeCategories
                    ? newsRepository.findAllByCategoriesId(categoryId, pageable, FETCH_OPTIONS.get("categories"))
                    : newsRepository.findAllByCategoriesId(categoryId, pageable);
        } else {
            news = includeCategories
                    ? newsRepository.findAll(pageable, FETCH_OPTIONS.get("categories"))
                    : newsRepository.findAll(pageable);
        }

        return includeCategories
                ? newsMapper.mapToList(news.getContent())
                : newsMapper.mapToListIgnoringCategories(news.getContent());
    }

    public void attachCategoriesToNews(long id, long[] categoryIds) {
        newsRepository.saveNewsCategories(id, categoryIds);
    }

    public void detachCategoriesToNews(long id, long[] categoryIds) {
        newsRepository.deleteNewsCategories(id, categoryIds);
    }

}