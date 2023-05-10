package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.mappers.NewsMapper;
import ru.hackaton.backend.models.domain.News;
import ru.hackaton.backend.repositories.NewsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final static String DEFAULT_SORT_OPTION = "updatedAt";

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private News getNewsById(long id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Новость с id " + id + " не была найдена!"));
    }

    public void createNews(NewsDto newsDto) {
        newsDto.setCreatedAt(LocalDateTime.now());
        newsDto.setUpdatedAt(LocalDateTime.now());
        newsRepository.save(newsMapper.toNews(newsDto));
    }

    public NewsDto findNewsById(long id) {
        return newsMapper.toDto(getNewsById(id));
    }

    public void updateNews(long id, NewsDto newsDto) {
        News dbNews = getNewsById(id);
        newsDto.setId(id);
        newsMapper.updateNewsFromDto(newsDto, dbNews);
        newsRepository.save(dbNews);
    }

    public void deleteNewsById(long id) {
        newsRepository.deleteById(id);
    }

    public List<NewsDto> findAllNews(Integer pageNum, Integer perPage) {
        if (perPage > 100) perPage = 100;
        return newsRepository.findAllButContent(
                PageRequest.of(pageNum, perPage, Sort.by(Sort.Direction.DESC, DEFAULT_SORT_OPTION))
        ).getContent();
    }

    public void attachCategoriesToNews(long id, long[] categoryIds) {
        newsRepository.saveNewsCategories(id, categoryIds);
    }

    public void detachCategoriesToNews(long id, long[] categoryIds) {
        newsRepository.deleteNewsCategories(id, categoryIds);
    }

}
