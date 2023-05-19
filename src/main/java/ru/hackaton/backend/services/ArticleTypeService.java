package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.ArticleTypeDto;
import ru.hackaton.backend.mappers.ArticleTypeMapper;
import ru.hackaton.backend.models.domain.ArticleType;
import ru.hackaton.backend.repositories.ArticleTypeRepository;
import ru.hackaton.backend.util.PageWrapper;


@Service
@RequiredArgsConstructor
public class ArticleTypeService {

    private final ArticleTypeRepository articleTypeRepository;

    private final ArticleTypeMapper articleTypeMapper;

    private ArticleType findArticleTypeById(long id) {
        return articleTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Тип статьи с id " + id + " не был найден!"));
    }

    public ArticleTypeDto createArticleType(ArticleTypeDto articleTypeDto) {
        ArticleType newArticleType = articleTypeRepository.save(articleTypeMapper.toArticleType(articleTypeDto));
        return articleTypeMapper.toDto(newArticleType);
    }

    public ArticleTypeDto getArticleTypeById(long id) {
        return articleTypeMapper.toDto(findArticleTypeById(id));
    }

    public void updateArticleType(long id, ArticleTypeDto articleTypeDto) {
        ArticleType articleType = articleTypeMapper.toArticleType(articleTypeDto);
        articleType.setId(id);
        articleTypeRepository.save(articleType);
    }

    public void deleteArticleTypeById(long id) {
        articleTypeRepository.deleteById(id);
    }

    public PageWrapper<ArticleTypeDto> getAllArticleTypes() {
        Page<ArticleType> page = articleTypeRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
        return new PageWrapper<>(page.getTotalElements(), articleTypeMapper.mapToList(page.getContent()));
    }

}