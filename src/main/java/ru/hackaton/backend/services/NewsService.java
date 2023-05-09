package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.NewsDto;
import ru.hackaton.backend.mappers.NewsMapper;
import ru.hackaton.backend.models.domain.News;
import ru.hackaton.backend.repositories.NewsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    private News getNewsById(long id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Новость с id " + id + " не была найдена!"));
    }

    public void createNews(NewsDto newsDto) {
        newsRepository.save(newsMapper.toNews(newsDto));
    }

    public NewsDto findNewsById(long id) {
        return newsMapper.toDto(getNewsById(id));
    }

    public void updateNews(long id, NewsDto newsDto) {
    }

    public void deleteNewsById(long id) {
        newsRepository.deleteById(id);
    }

    public List<NewsDto> findAllNews(Integer pageNum, Integer perPage) {
        if (perPage > 100) perPage = 100;
        List<News> news = newsRepository.findAllCompressed(pageNum, perPage);
        return newsMapper.map(news);
    }

}
