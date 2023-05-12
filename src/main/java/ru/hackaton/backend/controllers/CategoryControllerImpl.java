package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.CategoryDto;
import ru.hackaton.backend.services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public void create(CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
    }

    @Override
    public CategoryDto read(long id) {
        return categoryService.findCategoryById(id);
    }

    @Override
    public void update(long id, CategoryDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
    }

    @Override
    public void delete(long id) {
        categoryService.deleteCategoryById(id);
    }

    @Override
    public List<CategoryDto> readAll() {
        return categoryService.findAllCategories();
    }

}