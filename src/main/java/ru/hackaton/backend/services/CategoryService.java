package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.CategoryDto;
import ru.hackaton.backend.mappers.CategoryMapper;
import ru.hackaton.backend.models.domain.Category;
import ru.hackaton.backend.repositories.CategoryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Категория с id " + id + " не была найдена!"));
    }

    public void createCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toCategory(categoryDto));
    }

    public CategoryDto findCategoryById(long id) {
        return categoryMapper.toDto(getCategoryById(id));
    }

    public void updateCategory(long id, CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        category.setId(id);
        categoryRepository.save(category);
    }

    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }

    public List<CategoryDto> findAllCategories() {
        return categoryMapper.mapToList(categoryRepository.findAll());
    }

}